# 资源微服务
## OAuth2资源服务依赖配置
当客户端(web端、mobile移动端)带着token向资源服务器发起请求获取资源,资源服务器需要对请求中的token进行校验以及对资源进行授权，如果token校验和授权都通过即可返回相应的数据给客户端
- 导入OAuth2依赖
```xml
<!-- 导入oauth2的依赖 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>
```
- Oauth2提供的资源服务配置类为`ResourceServerConfigurerAdapter`，它实现了`ResourceServerConfigurer`接口，提供了两个`configure`方法
    - 第一个configure方法的参数`ResourceServerSecurityConfigurer`：是资源服务安全配置，比如Token该如何校验就是通过它来配置的
    - 第二个configure方法的参数`HttpSecurity`：用作WEB安全配置
## 资源服务器器配置类
```java
@Configuration
//开启资源服务配置
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 配置资源id ,对应oauth_client_details表中配置的resourceIds
     * 一个资源ID可以对应一个资源服务器，如果Token中不包含该资源ID就无法访问该资源服务器
     */
    public static final String RESOURCE_ID = "resource-server-A";

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * JWT令牌校验工具
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        //设置JWT签名密钥。它可以是简单的MAC密钥，也可以是RSA密钥
        jwtAccessTokenConverter.setSigningKey("duanjh");

        return jwtAccessTokenConverter;
    }

    /**
     * 资源服务器安全性配置
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        //资源ID，请求中的Token必须有用该资源ID的访问权限才可以访问该资源服务器
        resources.resourceId(RESOURCE_ID);

        //指定TokenStore，配置Token的校验方式，使用JWT校验
        resources.tokenStore(tokenStore());

        //无状态
        resources.stateless(true);

        //验证令牌的服务，令牌验证通过才允许获取资源，使用远程校验
        resources.tokenServices(resourceServerTokenServices());
    }

    /**
     * 资源服务令牌验证服务,通过远程校验令牌
     */
    @Bean
    public ResourceServerTokenServices resourceServerTokenServices() {
        //使用远程服务请求授权服务器校验token ， 即：资源服务和授权服务器不在一个主机
        RemoteTokenServices services = new RemoteTokenServices();

        //授权服务地址, 当浏览器访问某个资源时就会调用该远程授权服务地址去校验token，要求请求中必须携带token
        services.setCheckTokenEndpointUrl("http://localhost:8003/oauth/check_token");

        //客户端id，对应认证服务的客户端详情配置oauth_client_details表中的clientId
        services.setClientId("duanjh");

        //密钥，对应认证服务的客户端详情配置的秘钥
        services.setClientSecret("secret");

        return services;
    }

    /**
     * SpringSecurity的相关配置
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                // 校验scope必须为all，对应认证服务的客户端详情配置的clientId
                .antMatchers("/**").access("#oauth2.hasScope('all')")

                // 关闭跨域伪造检查
                .and().csrf().disable()

                // 把session设置为无状态，意思是使用了token，那么session不再做数据的记录
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
```
- `resources.tokenStore(tokenStore())`：配置Token的校验方式，使用的是JwtTokenStore，JWT自校验的方式。也可以使用`resources.tokenServices`指定一个`RemoteTokenServices`远程校验方案，这种方案的流程是当请求到达资源服务器，资源服务会带着请求中的token，向认证服务器发起远程校验(/oauth/check_token)
- `resources.resourceId(RESOURCE_ID)`：配置资源服务器ID，意思是访问这个资源服务器的请求中的Token必须包含这个资源ID的访问权限
- `.antMatchers("/**").access("#oauth2.hasScope(‘all’)")`：意思是Token必须拥有all的授权访问才可以访问到资源服务器
- **注意：这里在配置类上开启了@EnableGlobalMethodSecurity(prePostEnabled = true)，因为我这里需要使用方法授权的方式指明我们的controller方法需要什么样的权限才能访问，当然也可以使用web授权的方式(回顾web授权和方法授权)**
## 资源Controller配置
```java
@RestController
@RequestMapping("/data")
public class DataController {

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('data:all')")    // 如果资源服务器对token校验通过就能够访问该资源
    public String all(){
        return "您的token验证通过,已经访问到真正的资源 data:all";
    }

    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('data:add')")
    public String add(){
        return "您的token验证通过,已经访问到真正的资源 data:add";
    }

    @RequestMapping("/page")
    @PreAuthorize("hasAnyAuthority('data:page')")
    public String page(){
        return "您的token验证通过,已经访问到真正的资源 data:page";
    }
}
```
- **注意：需要在资源服务配置类`ResourceServerConfig`开启全局方法授权：`@EnableGlobalMethodSecurity(prePostEnabled = true)`**
- 携带token访问资源
  - 请求头携带`Authorization=Bearer token值`
  - 访问有权限资源
  ```
  请求：
    curl -X GET 'http://localhost:8004/data/all' -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLUEiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNzU1Njc2NjYzLCJhdXRob3JpdGllcyI6WyJkYXRhOnNlbGVjdCIsImRhdGE6ZGVsIiwiZGF0YTplZGl0IiwiZGF0YTphZGQiLCJkYXRhOmFsbCIsImhvbWUiXSwianRpIjoiNGM4NDBjNGItODRmNy00NjdhLWFkYmYtZGM4OWJiNTM5YzcwIiwiY2xpZW50X2lkIjoiZHVhbmpoIn0.hu7Hy7RKOEUoC13SIkOZhHFMHYEAnifb6E-v9HgfwPw' -H 'Cookie: loginTokens=00B8A9F68D5FED2AD05B4CD71056A21602242FB47CD4220994E9E4BBBA65B07B199FC3F4E2049367C65E2BE9F5DC8C3D1406C4F095A9574C09D62F370B187360FF13B5A3C89BE66FC614105823BD206F28C108EEDC826D1CBAD8B432A4D2402DF281CB79B6F6C955A14EA45B01259BD0'

  返回：
    您的token验证通过,已经访问到真正的资源 data:all
  ```
  - 访问无权限资源
  ```
  请求：
    curl -X GET 'http://localhost:8004/data/page' -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLUEiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNzU1Njc2NjYzLCJhdXRob3JpdGllcyI6WyJkYXRhOnNlbGVjdCIsImRhdGE6ZGVsIiwiZGF0YTplZGl0IiwiZGF0YTphZGQiLCJkYXRhOmFsbCIsImhvbWUiXSwianRpIjoiNGM4NDBjNGItODRmNy00NjdhLWFkYmYtZGM4OWJiNTM5YzcwIiwiY2xpZW50X2lkIjoiZHVhbmpoIn0.hu7Hy7RKOEUoC13SIkOZhHFMHYEAnifb6E-v9HgfwPw' -H 'Cookie: loginTokens=00B8A9F68D5FED2AD05B4CD71056A21602242FB47CD4220994E9E4BBBA65B07B199FC3F4E2049367C65E2BE9F5DC8C3D1406C4F095A9574C09D62F370B187360FF13B5A3C89BE66FC614105823BD206F28C108EEDC826D1CBAD8B432A4D2402DF281CB79B6F6C955A14EA45B01259BD0'
  
  返回：
    {
      "error": "access_denied",
      "error_description": "不允许访问"
    }
  ```
  - 请求资源之前，资源服务器会发送远程请求到授权服务器验证token的合法性，并且根据当前token获取权限列表，然后在进行授权，如果权限列表拥有资源(controller的方法)所需要的权限，即可访问成功
## 资源服务器总体配置
- 主要是配置如下内容
1. 通过`HttpSecurity`配置`oauth2.hasScope('all')`资源服务器的授权范围
2. 通过`ResourceServerSecurityConfigurer`配置资源服务器的`ResourceID`
3. 通过`ResourceServerSecurityConfigurer`配置资源服务器如何校验Token，如`RemoteTokenServices`远程校验
- 资源服务器的执行流程
  - 请求头中带着Token向资源服务器发送请求
  - 资源服务器收到请求，得到Token后通过`jwtAccessTokenConverter`转换器进行`Token的转换`得到Token中的认证授权信息
  - 资源服务器校验认证信息中是否拥有资源服务器配置的`授权范围(Scope)`、`资源ID(ResourceID)`，如果都验证通过，资源服务器会校验`权限列表是否包含当前访问的资源(controller)所需要的权限`
  - 若`授权范围(Scope)`、`资源ID(ResourceID)`、`方法授权`都校验通过则执行相关的方法返回资源
  
