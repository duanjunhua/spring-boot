package com.duanjh.auth.config.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-12 周二 17:01
 * @Version: v1.0
 * @Description: 授权服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /*------------------------------------ 客户端详情配置 --------------------------------------------*/
    /**
     * 密码编码器
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ClientDetailsService clientDetailsService() {

        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        // 设置密码编码器
        clientDetailsService.setPasswordEncoder(passwordEncoder);

        return clientDetailsService;
    }

    // 基于JDBC的客户端详情配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    /*------------------------------------ 令牌服务配置 --------------------------------------------*/

    /**
     * 认证管理器，需要配置
     */
    @Autowired
    AuthenticationManager authenticationManager;

    // JWT令牌校验工具
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        // 设置JWT签名密钥：可以是简单的MAC密钥，也可以是RSA密钥
        converter.setSigningKey("duanjh");

        return converter;
    }

    //令牌存储，基于JWT
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    // 配置令牌服务
    @Bean
    public AuthorizationServerTokenServices tokenServices() {

        //创建默认令牌服务
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        // 指定客户端详情配置
        tokenServices.setClientDetailsService(clientDetailsService());

        // 支持刷新token
        tokenServices.setSupportRefreshToken(true);

        // token存储方式
        tokenServices.setTokenStore(tokenStore());

        // 设置token增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter()));

        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        // 设置token有效期：5分钟
        tokenServices.setAccessTokenValiditySeconds(60*60*5);

        //刷新令牌默认有效时间
        tokenServices.setRefreshTokenValiditySeconds(60*60*5);

        return tokenServices;
    }

    /**
     * 授权码服务
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 配置令牌访问端点
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 密码模式需要
                .authenticationManager(authenticationManager)
                // 授权码模式服务
                .authorizationCodeServices(authorizationCodeServices())
                // 配置令牌管理服务
                .tokenServices(tokenServices())
                // 允许POST请求
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /*------------------------------------ 端点安全约束：配置令牌安全约束 --------------------------------------------*/

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 对应 /oauth/token_key 公开，获取公钥需要访问该端点
                .tokenKeyAccess("permitAll()")
                // 对应/oauth/check_token 路径公开，校验Token需要请求该端点
                .checkTokenAccess("permitAll()")
                // 允许客户端进行表单身份验证,使用表单认证申请令牌
                .allowFormAuthenticationForClients();
    }
}
