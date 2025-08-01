# OAuth2.0

--- 
## 一、SpringSecurity 认证
### 认证
对主体/用户身份的确认

### 授权
控制不同的用户访问不同的权限 ，用户认证成功后，就可以对某些资源进行访问， 但是不同的用户有不同的资源访问权利，那么对用户的授权也都不一样。授权的过程就是赋予不同用户不同权限的过程

### RBCA
基于角色的访问控制(Role-Based Access Control)，在 RBAC 中，权限与角色相关联，用户通过成为适当角色的成员而得到这些角色的权限；权限赋予给角色，而把角色又赋予用户。
RBAC 认为授权实际上是Who 、What 、How 三元组之间的关系，也就是Who 对What 进行How 的操作
- 主体（Who) ：权限的拥有者或主体（如：User，Role）
- 资源 (what)：操作或对象，如：页面，菜单，按钮，控制器(controller，hanler)等
- 权限(how) ：具体的权限， 如：新增、修改、删除

### 基于Session的认证
- 认证流程
    - ①用户提交认证请求
    - ②后端登录处理器（`LoginController`）接收登录信息，通过对应`LoginService`调用持久层`LoginDao`获取数据库用户信息并进行认证
    - ③认证成功，将用户认证信息`User`及对应的权限信息`Permission`设置到自定义上下文工具`SecurityContext`，存储到`Session`中
    - ④返回认证结果


- 认证检查流程
  - ①发起资源请求
  - ②判断`Session`是否有登录信息，通过`LoginCheckInterceptor`进行登录检查
  - ③如果登录信息不存在，返回错误信息；如果登录信息存在，进行第④步
  - ④返回访问资源


- 授权流程
`授权是约束用户对资源的访问权限。认证通过后，加载当前用户的权限列表进行权限检查`
  - ①发起资源请求
  - ②权限检查，通过`PermissionCheckInterceptor`访问`Session`中的用户权限信息进行校验
  - ③若无权限，返回错误信息；若有权限，进行第④步
  - ④返回访问资源

### 简易登录实现
SpringSecurity提供了一组可以在Spring应用上下文中配置的Bean，充分利用了Spring IoC，DI（控制反转Inversion of Control ,DI:Dependency Injection 依赖注入）和AOP（面向切面编程）功能，为应用系统提供声明式的安全访问控制功能
- 具体实现代码：
```java
// 只需配置WebSecurityConfig与编写控制器类SecurityAuthController即可实现，访问：http://localhost:8080/security
package com.duanjh.oauth2.security;
```
- 依赖项:
```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
```
- 认证流程及原理

SpringSecurity是基于Filter实现认证和授权，底层通过FilterChainProxy代理去调用各种Filter(Filter链)，Filter通过调用AuthenticationManager完成认证，通过调用AccessDecisionManager完成授权
- 过滤器链

  `SecurityContextPersistenceFilter→UsernamePasswordAuthenticationFilter→BasicAuthenticationFilter→RememberAuthenticationFilter→ExceptionTranslationFilter→FilterSecurityInterceptor`
  - SecurityContextPersistenceFilter：Filter的入口和出口，用来将`SecurityContext`(认证的上下文，里面有登录成功后的认证授权信息)对象持久到Session的`Filter`，同时会把`SecurityContext`设置给`SecurityContextHolder`方便我们获取用户认证授权信息
  - UsernamePasswordAuthenticationFilter：默认拦截`/login`登录请求，处理表单提交的登录认证，将请求中的认证信息包括`username,password`等封装成`UsernamePasswordAuthenticationToken`，然后调用`AuthenticationManager`的认证方法进行认证
  - BasicAuthenticationFilter：基本认证，支持httpBasic认证方式的Filter
  - RememberAuthenticationFilter：记住我功能实现的Filter
  - AnonymousAuthenticationFilter：用来处理匿名访问的资源，如果用户未登录，SecurityContext中没有Authentication，就会创建匿名的Token(AnonymousAuthenticationToken),然后通过SecurityContextHodler设置到SecurityContext中
  - ExceptionTranslationFilter：用来捕获FilterChain所有的异常，进行处理，但是只会处理AuthenticationException和AccessDeniedException异常，其他的异常会继续抛出
  - FilterSecurityInterceptor：用来做授权的Filter,通过父类`(AbstractSecurityInterceptor.beforeInvocation)`调用`AccessDecisionManager.decide`方法对用户进行授权
- 相关类
  - Authentication：认证对象，用来封装用户的认证信息(账户状态，用户名，密码，权限等)，常用实现类：
    - UsernamePasswordAuthenticationToken：用户名密码登录的Token
    - AnonymousAuthenticationToken：针对匿名用户的Token
    - RememberMeAuthenticationToken：记住我功能的的Token
  - AuthenticationManager：用户认证的管理类，所有的认证请求（比如login）都会通过提交一个封装了到了登录信息的Token对象给`AuthenticationManager`的`authenticate()`方法来实现认证；认证成功后，返回一个包含了认 证
    信息的`Authentication`对象
  - AuthenticationProvider：认证的具体实现类，一个provider是一种认证方式的实现；主流的认证方式它都已经提供了默认实现，比如`DAO、LDAP、CAS、OAuth2`等。AuthenticationManager默认的实现类是ProviderManager
  - UserDetailService：用户的认证通过Provider来完成，而Provider会通过UserDetailService拿到数据库（或内存）中的认证信息然后和客户端提交的认证信息做校验
  - SecurityContext：通过认证之后，会为这个用户生成一个唯一的`SecurityContext`，里面包含用户的认证信息`Authentication`。通过`SecurityContext`我们可以获取到用户的标识`Principle`和授权信息`GrantedAuthrity`。在系统的任何地方只要通过`SecurityHolder.getSecruityContext()`就可以获取到`SecurityContext`
- 认证流程
  - 传统认证流程

    ![传统认证](notes/tradition.png)
  - SpringSecurity认证流程

    ![SpringSecurity认证](notes/springSecurity.png)
    1. [x] **SecurityContext是一个和当前线程绑定的工具，在代码的任何地方都可以通过SecurityContextHolder.getContext()获取到登陆信息**
    - `SecurityContextPersistenceFilter`是整个filter链的入口和出口，请求开始会从SecurityContextRepository中 获取SecurityContext对象并设置给SecurityContextHolder。它的作用为：
      - 请求来的时候将包含了认证授权信息的SecurityContext对象从SecurityContextRepository中取出交给SecurityContextHolder工具类，方便我们通过SecurityContextHolder获取SecurityContext从而获取到认证授权信息
      - 请求走的时候又把SecurityContextHolder清空
      ```java
      public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
  
          HttpRequestResponseHolder holder = new HttpRequestResponseHolder(request, response);
          //从SecurityContextRepository获取到SecurityContext 
          SecurityContext contextBeforeChainExecution = repo.loadContext(holder);
  
          try {
              //把 securityContext设置到SecurityContextHolder，如果没认证通过，这个SecurtyContext就是空的
              SecurityContextHolder.setContext(contextBeforeChainExecution);
              //调用后面的filter，比如掉用usernamepasswordAuthenticationFilter实现认证
              chain.doFilter(holder.getRequest(), holder.getResponse());
          }finally {
              //如果认证通过了，这里可以从SecurityContextHolder.getContext();中获取到SecurityContext
              SecurityContext contextAfterChainExecution = SecurityContextHolder.getContext();
              //删除SecurityContextHolder中的SecurityContext 
              SecurityContextHolder.clearContext();
              //把SecurityContext 存储到SecurityContextRepository
              repo.saveContext(contextAfterChainExecution, holder.getRequest(),holder.getResponse());
              request.removeAttribute(FILTER_APPLIED);
              if (debug) {
                  logger.debug("SecurityContextHolder now cleared, as request processing completed");
              }
          }
      }
      ```
    - `UsernamePasswordAuthenticationFilter`是拦截“/login”登录请求，处理表单提交的登录认证，将请求中的认证信息包括`username,password`等封装成UsernamePasswordAuthenticationToken，然后调用AuthenticationManager的认证方法进行认证；后面认证成功，请求会重新回到UsernamePasswordAuthenticationFilter，然后会通过其父类AbstractAuthenticationProcessingFilter.successfulAuthentication方法将认证对象封装成SecurityContext设置到SecurityContextHolder中，后续请求又会回到SecurityContextPersistenceFilter
    - `AuthenticationManager`默认走的实现类是ProviderManager，它会找到能支持当前认证的AuthenticationProvider实现类调用器authenticate方法执行认证，认证成功后会清除密码，然后抛出AuthenticationSuccessEvent事件
    - `DaoAuthenticationProvider`是根据传入的Token中的username调用UserDetailService加载数据库中的认证授权信息(UserDetails)，然后使用PasswordEncoder对比用户登录密码是否正确。真正的认证逻辑是通过父类AbstractUserDetailsAuthenticationProvider.authenticate方法完成的
      - `additionalAuthenticationChecks`:通过passwordEncoder比对密码
      - `retrieveUser`:根据用户名调用UserDetailsService加载用户认证授权信息
      - `createSuccessAuthentication`:登录成功，创建认证对象Authentication
  - 在SpringSecurity的整个认证流程中，除了UserDetailsService需要我们自己定义外，其他的的组件都可以使用默认的
- 授权流程

  授权流程是通过`FilterSecurityInterceptor`拦截器来完成，FilterSecurityInterceptor通过调用SecurityMetadataSource来获取当前访问的资源所需要的权限，然后通过调用AccessDecisionManager投票决定当前用户是否有权限访问当前资源
  - 流程：
    - ①客户端向某个资源发起请求，请求到达FilterSecurityInterceptor，然后会调用其父类AbstractSecurityInterceptor的beforeInvocation方法做授权之前的准备工作
    - ②在beforeInvocation法中通过`SecurityMetadataSource…getAttributes(object)`获得资源所需要的访问权限，通过`SecurityContextHolder.getContext().getAuthentication()`获取当前认证用户的认证信息
    - ③然后FilterSecurityInterceptor通过调用AccessDecisionManager.decide(authenticated,object,attributes)进行授权，使用投票器投票来决定用户是否有资源访问权限
    - ④投票通过，请求放行，响应对应的资源给客户端
  - AccessDecisionManager接口有三个实现类，通过`AccessDecisionVoter`投票器完成投票，三种投票策略如下：
    - `AffirmativeBased`: 只需有一个投票赞成即可通过
    - `ConsensusBased`：需要大多数投票赞成即可通过，平票可以配置
    - `UnanimousBased`：需要所有的投票赞成才能通过
  - 还可以通过`RoleVoter、AuthenticatedVoter`完成投票，`RoleVoter`处理ConfigAttribute是以“ROLE_”开头的，`AuthenticatedVoter`用来区分匿名用户与通过Remember-Me认证的用户和完全认证的用户(登录后的)
- HttpSecurity授权Api：
  - `anyRequest()`: 任意请求
  - `requestMatchers(String ...patterns)`：匹配某些资源路径
  - `authenticated()`：保护URL访问需要登录后
  - `permitAll()`：指定URL可直接访问
  - `hasRole(String role)`：资源需要用户拥有什么样的role才能访问
  - `hasAuthority(String authority)`：资源需要用户拥有什么样的权限才能访问
  - `hasAnyRole(String …roles)`：资源拥有指定角色中的一个就能访问
  - `hasAnyAuthority(String … authorities)`：资源拥有指定权限中的一个就能访问
  - `access(String attribute)`：该方法使用SPEL表达式,可以创建复杂的限制
- Web授权
  - 配置HttpSecurity
    ```
    ...
    .authorizeHttpRequests(request -> {
      // 从权限表查询出了所有的资源并进行一一授权,指定哪个资源需要哪个权限才能访问
      if(CollectionUtils.isNotEmpty(permissions)){
        // 权限
        permissions.stream().forEach(p -> {
          request.requestMatchers(p.getResource()).hasAuthority(p.getSn());
        });
      }
      request.anyRequest().authenticated();
    })
    ```
  - 配置UserDetailService
    ```
     // 加载权限：加载用户认证信息的时候就把用户的权限信息一并加载
     List<GrantedAuthority> permissions = new ArrayList<>();
     List<Permission> authorizeds = permissionRepository.findPermissionsByUserId(login.getId());
     if(CollectionUtils.isNotEmpty(authorizeds)){
        authorizeds.stream().forEach(v -> {
            permissions.add(new SimpleGrantedAuthority(v.getSn()));
        });
     }
    ``` 
- 方法授权
  - `@Secured`：标记方法需要有什么样的权限才能访问，这个注解需要在配置类上开启授权注解`@EnableGlobalMethodSecurity(securedEnabled=true)`支持
    - `@Secured(“IS_AUTHENTICATED_ANONYMOUSLY”)`：方法可以匿名访问
    - `@Secured(“ROLE_DEPT”)`： 需要拥有部门的角色才能访问，`ROLE_`前缀是固定的
    - `@Secured`注解授权是需要加上前缀`ROLE_`
  ```
  @Configuration
  @EnableWebSecurity
  @EnableGlobalMethodSecurity(securedEnabled=true)
  public class WebSecurityConfig  {
    ...
  }
  
  
  @ResponseBody
  @Secured("ROLE_data:all")   // 表示当前资源需要“ROLE_data:all”权限才能访问
  @RequestMapping("/data/all")
  public String all(){
      return "data.all";
  }
  ```
  - `@PreAuthorize`：适合进入方法前的权限验证，拥有和Secured同样的功能，甚至更强大，该注解需要在配置类开启`@EanbleGlobalMethodSecurity(prePostEnabled=true)`方法授权支持
    - `@PreAuthorize("isAnonymous()")`：方法匿名访问
    - `@PreAuthorize("hasAnyAuthority('data:list','user:list')")`：拥有`data:list`或者`user:list`的权限能访问
    - `@PreAuthorize("hasAuthority('data:del') and hasAuthority('user:view')")` : 拥有`data:del`权限和`user:view`权限才能访问
    - 该标签不需要有固定的前缀
    ```
    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(
        prePostEnabled = true   // 启用@PreAuthorize
    )
    public class WebSecurityConfig  {
        ....
    }
    
    
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('data:add','data:edit')")    //指明了方法必须要有 data:add 或者 data:edit的权限才能访问
    @RequestMapping("/data/edit")
    public String edit(){
        return "data.edit";
    }
    ```
  - `@PostAuthorize`：不常用，适合在方法执行后再进行权限验证，使用该注解需要在配置类开启`@EanbleGlobalMethodSecurity(prePostEnabled=true)`方法授权支持
- 认证结果处理
  - 在前后端分离的项目通常是使用Ajax请求完成认证，需要返回一个JSON结果告知前端认证结果，然后前端自行跳转页面，需要做以下开发
    - 实现处理器
      - 认证成功（`AuthenticationSuccessHandler`）：复写`onAuthenticationSuccess`方法，其中一个参数`Authentication`封装了认证信息、用户信息`UserDetails`
      - 认证失败
    - 在配置中配置自定义处理器
    ```
        // 设置登录成功页，如果使用的是自定义AuthenticationSuccessHandler返回json格式，需要将这个注释掉
        // formLogin.defaultSuccessUrl("/home");

        // 成功处理：自定义AuthenticationSuccessHandler处理器处理返回JSON数据
        formLogin.successHandler(new PersonalAuthorizationHandler());
        // 失败处理
        formLogin.failureHandler(new PersonalAuthorizationFailureHandler());
    ```
- 授权结果处理：当认证或授权检查失败，需要返回自己的失败结果信息时，可通过`HttpSecurity`设置授权失败结果处理器,内部通过`ExceptionTranslationFilter`调用`AuthenticationEntryPoint`实现匿名用户授权失败结果处理，`ExceptionTranslationFilter`通过`AccessDeniedHandler`来处理授权失败结果处理
  - 定义`AccessDeineHandler`用来解决认证过的用户访问无权限资源时的异常
  - `AuthenticationEntryPoint`用来解决匿名用户访问无权限资源时的异常
  - 异常处理器配置
  ```
  httpSecurity.
  ...
  .exceptionHandling(resolver -> {
      resolver.accessDeniedHandler(new AccessDeniedHandler() {
          @Override
          public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
              log.info("此处写解决认证过的用户访问无权限资源时的异常的处理");
          }
      }).authenticationEntryPoint(new AuthenticationEntryPoint() {
          @Override
          public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
              log.info("此处写解决认证过的用户访问无权限资源时的异常的处理");
          }
      });
  })
  ...
  .build();
  ```
### 记住密码功能
- 核心流程
  - 用户发起登录勾选了记住我，在一定的时间内再次访问该网站会默认登录成功，即使浏览器退出重新打开也是如此，这个功能需要借助浏览器的cookie实现
  - 核心流程
  ![记住密码流程](notes/remember.png)
    - 认证成功`UsernamePasswordAuthenticationFilter`会调用`RememberMeServices`创建`Token`，同时`RemeberMeService`会调用`TokenRepository`将`Token`写入数据库持久化,然后`RemeberMeService`通过`Reponse.addCookie`把`Token`写到浏览器的`Cookies`中
    - 当浏览器再次发起请求会进入`RemeberMeAuthenticationFilter`，将请求中获取到的`token`交给`RemeberMeService`
    - `RemeberMeService`调用`TokenRepository`去数据库中根据`Token`查询用户名
    - 调用`UserDetilasService.loadUserByUsername`根据用户名获取用户认证信息
    - 通过`authenticationManager.authenticate`，做一次认证，然后把用户信息放入上下文对象中
- 功能实现
  - 在`WebSecurityConfig`中配置`TokenRepository`
  ```
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(context.getBean(DataSource.class));
        // 启动创建persistent_logs表，首次启动时可设置true，后面必须设置false，也可直接自行创建表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
  
    // 表persistent_logs
    CREATE TABLE persistent_logins (
      username varchar(64) NOT NULL COMMENT '用户登录名',
      series varchar(64) NOT NULL COMMENT,
      token varchar(64) NOT NULL COMMENT '用户登录Token',
      last_used timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (series)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
  ```
  - 配置`RememberMe`：修改认证服务配置`WebSecurityConfig`的`HttpSecurity`配置，增加rememberMe
  ```
  httpSecurity.
  ...
  // 配置Remember Me
  .rememberMe(rmc -> {
      // 持久化
      rmc.tokenRepository(persistentTokenRepository())
          // 过期时间，设置3600秒，即1小时
          .tokenValiditySeconds(3600)
          // 用于加载用户认证信息
          .userDetailsService(userDetailService);

  })
  ```
  - 访问登录页面进行登录，勾选记住我；登录成功后，退出浏览器；重新打开浏览器，直接访问资源-不需要登录就能访问；如果你观察浏览器的cookie会发现一个Token，该Token和persistent_logins中存储的Token对应
---
## 二、SpringCloud+Security+Oauth2
在微服务架构下有很多的服务，每个微应用都需要对访问进行认证检查和权限控制，客户端发起一个请求需要考虑如何让用户的认证状态通知到所有的微服务中，尤其是请求来源于多种客户端如浏览器、移动端、三方程序，服务之间访问时，微服务的授权变得更加麻烦，再加上本地Session在微服务(集群/分布式)环境中存在Session不同步的问题
### 微服务(分布式)授权方案
#### CAS单点登录
CAS是一种基于Cookie实现的单点登录方案、一个比较老的解决方案。分为`CAS Server`端和`CAS Client`端，Server端负责用户的登录流程，Client端需要整合到各个系统中，实现Demo可参考`https://github.com/duanjunhua/spring-boot/tree/master/springboot-cas`
![CAS访问流程](notes/cas.png)
1. 浏览器请求系统A，系统A检查到没有登录，重定向到认证服务，并且携带参数service，这个参数的作用是在认证中心认证通过之后会再跳回系统A的重定向地址 
2. 请求跳转到认证中心，认证中心接收到请求，返回登录页面，用户提交登录信息
3. 认证中心执行登录逻辑，认证成功，生成ticket（ST）和TGC，认证中心会将TGC写到浏览器的cookie，有这个东西说明用户登录过，通过TGC可以找到TGT
4. 认证中心将请求再重定向到系统A(根据之间的service参数回调地址)，并且携带一个ticket参数
5. 请求跳转回了系统A，系统A得到ticket，向认证中心发起请求校验ticket的合法性
6. 认证中心验证ticket通过，向client返回对应的用户信息
7. 浏览器请求系统B时，会重定向到认证服务，并且携带参数service及Cookie携带TGC，此后B的认证流程类似3~5

**注意：CAS方案每个面向用户的服务都必须与认证服务交互，由此会产生大量非常琐碎的网络流量和重复的工作，当微应用较多时，这种方案的弊端会更加明显且CAS不太适合移动端认证方案**
#### 分布式Session+网关
是在网关做登录，以及登录检查，登录会话通过Redis进行分布式会话存储，后端微服务可以从共享会话Redis中获取认证信息；当访问资源的时候携带者Token去后台，在网关层根据Token检查分布式会话是否完成登录
![分布式访问流程](notes/distributed.png)
1. 客户端发起请求，网关实现登录逻辑，登录信息存储到Redis，并且根据Redis的key创建一个Token
2. 将Token返回给浏览器，浏览器将Token存储起来
3. 浏览器在访问资源的时候请求到网关并携带Token,网关获取到Token，从Redis中获取用户登录信息做检查，没有问题就继续转发Token到后续服务
4. 在后续服务中可以通过Token去Redis中获取认证信息

**注意：该案需要注意对共享会有一定的保护机制、实现有一定难度**
#### 客户端Token+网关
是一种比较常用的认证方案，优点在于Token中携带了用户信息在服务之间传输，做到了无状态，可以通过`JWT`等安全机制加密Token保证Token的安全性

![客户端Token访问流程](notes/token.png)
1. 客户端发起认证请求，使用JWT等加密方式生成安全的Token，Token中携带了认证授权信息，然后返回给客户端
2. 客户端存储Token，后续客户端需要访问资源时，携带者Token请求
3. 客户端发起请求，在网关层对Token进行统一检查，检查通过Token继续携带到后端访问中，如果涉及到大量的用户信息的存放，可以使用Redis来进行存储
4. 后续服务获取到Token即可获取到用户信息

**注意：优点是可以做到无状态，因为Token中包含了用户信息，服务端不用考虑存储用户信息；缺点在于Token过长造成的网络传输的开销**
#### 其他方案
如使用SpirngSession+Redis实现Session共享，或是配置服务器之间的Session同步，亦或是使用Nginx的负载均衡策略url hash一致性来解决
### OAuth2（SpringCloud+SpringSecurity+OAuth2+JWT ）
![OAuth2认证方案](notes/oauth2.png)

**认证服务：即Oauth2授权服务，负责认证逻辑(登录)和颁发令牌(token)；网关：即Oauth2资源服务，负责token统一鉴权**
1. 客户端访问资源，网关检查未登录，向认证服务发起认证请求，认证服务执行认证流程
2. 认证成功，认证服务根据用户认证信息，授权信息颁发Token，Token中包含了认证授权信息
3. 客户端存储Token，并向资源服务发起请求，请求头携带Token
4. 请求先到达网关，可以在网关层校验Token，也可以不在网关层校验Token，而是在每个资源服务器上校验Token
5. Token校验通过，资源服务获取到Token中的权限信息对资源进行授权，授权成功返回资源数据，授权失败返回错误
6. 如果请求需要多个资源服务共同完成，那么我们还需要考虑在服务调用时把Token通过请求头传递给下一个被调用的微服务进行授权

#### 相关概念及流程、授权模式
- Oauth（Open Authorization）协议为用户资源的授权提供了一个安全的、开放而又简易的标准；Oauth的授权不会使第三方触及到用户的帐号信息，即第三方无需使用用户的用户名与密码就可以申请获得该用户资源的授权
- Oauth2的授权流程

![Oauth2授权流程](notes/oauth_grant.png)
- 授权模式（四种）
  - 授权码模式：功能最完整、流程最严密的授权模式
    - ①用户访问客户端，未登录跳转到认证服务器
    - ②用户选择是否给予客户端授权
    - ③用户授权后，认证服务器将用户导向客户端事先指定的“重定向URI”，并携带一个授权码
    - ④客户端收到授权码，附上早先的“重定向URI”，向认证服务器申请令牌（在客户端的后台的服务器上完成的，对用户不可见）
    - ⑤认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（`access token`）和更新令牌（`refresh token`）
    - 获取授权码请求参数：
      - `response_type`：表示授权类型，必选项，此处的值固定为"code"
      - `client_id`：表示客户端的ID，必选项
      - `redirect_uri`：表示重定向URI，可选项
      - `scope`：表示申请的权限范围，可选项
      - `state`：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值
      ```
      GET /authorize?response_type=code&client_id=yhzsduanjh&state=yhzs&redirect_uri=https%3A%2F%2Fwww%2Eduanjh%2Ecom%2Fcb HTTP/1.1
      Host: www.duanjh.com
      ```
      - 返回授权码数据格式（服务器回应客户端的URI，包含以下参数）
        - `code`：表示授权码，必选项。该码的有效期应该很短，通常设为10分钟，客户端只能使用该码一次，否则会被授权服务器拒绝。该码与客户端ID和重定向URI，是一一对应关系
        - `state`：如果客户端的请求中包含这个参数，认证服务器的回应也必须一模一样包含这个参数
        ```
          HTTP/1.1 302 Found
          Location: https://www.duanjh.com/cb?code=SplxlOBeZQQYbYS6WxSbIA&state=yhzs
        ```
    - 获取令牌请求参数（客户端向认证服务器申请令牌的HTTP请求，包含以下参数）
      - `grant_type`：表示使用的授权模式，必选项，此处的**值固定**为`authorization_code`
      - `code`：表示上一步获得的授权码，必选项
      - `redirect_uri`：表示重定向URI，必选项，且必须与获取授权码中的该参数值保持一致
      - `client_id`：表示客户端ID，必选项
      ```
      POST /token HTTP/1.1
      Host: com.duanjh.com
      Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW
      Content-Type: application/x-www-form-urlencoded
      grant_type=authorization_code&code=SplxlOBeZQQYbYS6WxSbIA&redirect_uri=https%3A%2F%2Fcom%2Eduanjh%2Ecom%2Fcb
      ```
      - 返回的访问令牌数据格式（认证服务器发送的HTTP回复）
        - `access_token`：表示访问令牌，必选项
        - `token_type`：表示令牌类型，该值大小写不敏感，必选项，可以是bearer类型或mac类型
        - `expires_in`：表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间
        - `refresh_token`：表示更新令牌，用来获取下一次的访问令牌，可选项
        - `scope`：表示权限范围，如果与客户端申请的范围一致，此项可省略
        ```
        HTTP/1.1 200 OK
        Content-Type: application/json;charset=UTF-8
        Cache-Control: no-store
        Pragma: no-cache    // HTTP头信息中明确指定不得缓存
        {
           "access_token":"2YotnFZFEjr1zCsicMWpAA",
           "token_type":"duanjh",
           "expires_in":3600,
           "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
           "example_parameter":"duanjh_value"
        }
        ```
  - 简化模式：不通过第三方应用程序的服务器，直接在浏览器中向认证服务器申请令牌，跳过了“授权码”这个步骤。所有步骤在浏览器中完成，令牌对访问者是可见的，且客户端不需要认证
    - ①客户端将用户导向认证服务器（HTTP请求参数如下）
      - `response_type`：表示授权类型，此处的值固定为"token"，必选项
      - `client_id`：表示客户端的ID，必选项
      - `redirect_uri`：表示重定向的URI，可选项
      - `scope`：表示权限范围，可选项
      - `state`：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值
      ```
      GET /authorize?response_type=code&client_id=yhzsduanjh&state=yhzs&redirect_uri=https%3A%2F%2Fwww%2Eduanjh%2Ecom%2Fcb HTTP/1.1
      Host: www.duanjh.com
      ```
    - ②用户决定是否给于客户端授权
    - ③用户给予授权后认证服务器将用户导向客户端指定的“重定向URI”，并在URI的Hash部分包含了访问令牌（认证服务器回应客户端的URI，包含以下参数）
      - `access_token`：表示访问令牌，必选项
      - `token_type`：表示令牌类型，该值大小写不敏感，必选项
      - `expires_in`：表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间
      - `scope`：表示权限范围，如果与客户端申请的范围一致，此项可省略
      - `state`：如果客户端的请求中包含这个参数，认证服务器的回应也必须一模一样包含这个参数
      ```
      HTTP/1.1 302 Found
      Location: http://www.duanjh.com/cb#access_token=2YotnFZFEjr1zCsicMWpAA&state=yhzs&token_type=duanjh&expires_in=3600
      ```
      **注意：认证服务器用HTTP头信息的Location栏，指定浏览器重定向的网址。注意，在这个网址的Hash部分包含了令牌**
    - ④浏览器向资源服务器发出请求，其中不包括③收到的Hash值
    - ⑤资源服务器返回一个网页，其中包含的代码可以获取Hash值中的令牌
    - ⑥浏览器执行⑤获得的脚本，提取出令牌
    - ⑦浏览器将令牌发给客户端
  - 密码模式：用户向客户端提供自己的用户名和密码（用户必须把自己的密码给客户端，但是客户端不得储存密码）
    - ①用户向客户端提供用户名和密码
    - ②客户端将用户名和密码发给认证服务器，向后者请求令牌（客户端发出的HTTP请求，包含以下参数）
      - `grant_type`：表示授权类型，此处的值固定为“password”，必选项
      - `username`：表示用户名，必选项
      - `password`：表示用户的密码，必选项
      - `scope`：表示权限范围，可选项
      ```
      POST /token HTTP/1.1
      Host: www.duanjh.com
      Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW
      Content-Type: application/x-www-form-urlencoded

      grant_type=password&username=duanjh&password=duanjh@123
      ```
      **注意：整个过程中，客户端不得保存用户的密码**
    - ③认证服务器确认无误后，向客户端提供访问令牌
  - 客户端模式：客户端以自己的名义，而不是以用户的名义，向“服务提供商”进行认证（在这种模式中，用户直接向客户端注册，客户端以自己的名义要求"服务提供商"提供服务）
    - ①客户端向认证服务器进行身份认证，并要求一个访问令牌（客户端发出的HTTP请求，包含以下参数）
      - `grant_type`：表示授权类型，此处的值固定为"clientcredentials"，必选项
      - `scope`：表示权限范围，可选项
      ```
      POST /token HTTP/1.1
      Host: www.duanjh.com
      Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW
      Content-Type: application/x-www-form-urlencoded

      grant_type=client_credentials
      ```
    - ②认证服务器确认无误后，向客户端提供访问令牌
- 刷新令牌 

如果用户访问的时候，客户端的"访问令牌"已经过期，则需要使用"更新令牌"申请一个新的访问令牌，客户端发出更新令牌的HTTP请求，包含以下参数
  - `grant_type`：表示使用的授权模式，此处的值固定为"refreshtoken"，必选项
  - `refresh_token`：表示早前收到的更新令牌，必选项
  - `scope`：表示申请的授权范围，不可以超出上一次申请的范围，如果省略该参数，则表示与上一次一致
  ```
  POST /token HTTP/1.1
  Host: www.duanjh.com
  Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW
  Content-Type: application/x-www-form-urlencoded

  grant_type=refresh_token&refresh_token=tGzv3JOkF0XG5Qx2TlKWIA
  ```
### JWT
#### JWT概念
`JSON Web Token（JWT）`是一个非常轻巧的规范。这个规范允许我们使用JWT在用户和服务器之间传递安全可靠的信息。JWT以`JSON`对象的形式安全传递信息，客户端身份经过服务器验证通过后，会生成带有签名的 JSON 对象并将它返回给客户端

**使用JWT生产的Token是安全的,它实现了数据的安全传输，并且Token中可以携带认证信息，做到真正的无状态，是“客户端Token”模式授权方案的不二选择**
#### JWT特点
 - 基于JSON，方便解析
 - 可以在令牌中定义内容，方便扩展
 - 非对称加密算法即数字签名，JWT防篡改
 - Token携带认证信息，无效访问服务器即可完成权限校验，做到“无状态”
#### JWT组成（三部分）
- 头部：JSON格式，描述JWT的最基本的信息。如签名算法`{"typ":"JWT","alg":"HS256"}`，这里指明了签名算法是HS256算法，使用过程中会对该JSON进行BASE64编码
- 载荷：JSON格式，包含了需要的数据内容，也需要BASE64编码
- 签名：通过指定的算法生成哈希，以确保数据不会被篡改jwt的第三部分是一个签证信息，这个签证信息由三部分组成：`head(base64编码后的)`、`playload(base64编码后的)`、`secret(秘钥)签名后的密文`构成JWT第三部分
#### JWT令牌创建
- 引入JWT依赖
```xml
<dependency>
 <groupId>io.jsonwebtoken</groupId>
 <artifactId>jwt</artifactId>
 <version>0.9.0</version>
</dependency>

<!-- JDK1.8 以上还需要引入jaxb-api -->
<dependency>
  <groupId>javax.xml.bind</groupId>
  <artifactId>jaxb-api</artifactId>
  <version>2.3.1</version>
</dependency>
```
- 令牌签发
```java
public static String generateJwtToken(String subject) {
    //系统时间
    long now = System.currentTimeMillis();

    //计算过期时间30秒过期
    long exp = now+1000*30;

    //使用Jwts签发令牌
    JwtBuilder jwtBuilder = Jwts.builder()
            //设置唯一编号
            .setId( "duanjh" )
            //主题，可以扔一个JSON
            .setSubject(subject)
            //签发时间
            .setIssuedAt(new Date())
            //过期时间
            .setExpiration(new Date(exp))
            //扩展数据，角色
            .claim("roles","admin")
            //使用HS256算法，并设置SecretKey(字符串)，SecretKey长度必须大于等于4
            .signWith(SignatureAlgorithm.HS256, "duanjh_password" );

    return jwtBuilder.compact();
}
```