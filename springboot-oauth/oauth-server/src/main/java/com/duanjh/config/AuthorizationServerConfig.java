package com.duanjh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Author: Michael J H Duan[Junhua Duan]
 * @Date: 2023-05-15 17:11
 * @Version: V1.0
 * @Description: 认证中心配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 客户端详情配置，比如密钥
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //TODO：一般存储在数据库中
        //存储在内存中
        clients.inMemory()
                //客户端ID
                .withClient("duanjh-client")
                //客户端密钥
                .secret(new BCryptPasswordEncoder().encode("duanjh-secret"))    //指定秘钥，使用加密算法加密了，秘钥为duanjh-secret
                //资源ID，唯一：比如将订单服务作为一个资源；可以设置多个
                .resourceIds("duanjh-res")
                //授权模式：总共4种：authorization_code、password、client_credentials、implicit； refresh_token表示认证中心支持令牌刷新
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                //允许的授权范围，客户端的权限，此处的“all”只是一种标识，可自定义，为后续的子资源服务进行权限控制
                .scopes("all")
                //是否需要授权，false表示不需要用户点击确认授权直接返回授权码
                .autoApprove(false)
                //授权码模式的回调地址
                .redirectUris("https://www.baidu.com");
    }

    /**
     * 令牌端点安全约束配置，比如/oauth/token对哪些开放
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                // 开启/oauth/token_key验证端口权限
                .tokenKeyAccess("permitAll()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("permitAll()")
                // 表示支持client_id 和 client_secret做登录认证
                .allowFormAuthenticationForClients();
    }

    /**
     * 令牌访问端点的配置
     *  配置了授权码模式所需要的服务，AuthorizationCodeServices
     *  配置了密码模式所需要的AuthenticationManager
     *  配置了令牌管理服务，AuthorizationServerTokenServices
     *  配置/oauth/token申请令牌的uri只允许POST提交
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                // 授权码模式所需要的authorizationCodeServices
                .authorizationCodeServices(authorizationCodeServices())
                // 密码模式所需要的authenticationManager
                .authenticationManager(authenticationManager)
                // 令牌管理服务，无论哪种模式都需要
                .tokenServices(tokenServices())
                // 只允许POST提交访问令牌，uri：/oauth/token
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
        /**
         * spring Security框架默认的访问端点有如下6个：
         *  /oauth/authorize：获取授权码的端点
         *  /oauth/token：获取令牌端点
         *  /oauth/confifirm_access：用户确认授权提交端点
         *  /oauth/error：授权服务错误信息端点
         *  /oauth/check_token：用于资源服务访问的令牌解析端点
         *  /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话
         */

    }

    /**
     * 授权码模式的services，使用授权码模式authorization_code必须注入
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        //TODO: 一般存储在数据库中
        //存储在内存中
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * 令牌管理服务的配置
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();

        //客户端端配置策略
        services.setClientDetailsService(clientDetailsService);

        //支持令牌的刷新
        services.setSupportRefreshToken(true);

        //令牌服务，配置令牌的存储（这里存放在内存中）
        services.setTokenStore(tokenStore);

        //access_token的过期时间：2小时
        services.setAccessTokenValiditySeconds(60 * 60 *2);

        //refresh_token的过期时间：3天
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);

        return services;
    }
}
