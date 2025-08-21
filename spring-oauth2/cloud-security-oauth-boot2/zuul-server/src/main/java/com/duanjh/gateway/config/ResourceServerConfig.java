package com.duanjh.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-20 周三 14:59
 * @Version: v1.0
 * @Description:
 */
@Configuration
public class ResourceServerConfig {

    // 设置JWT签名密钥。它可以是简单的MAC密钥，也可以是RSA密钥
    private static final String SIGN_KEY = "duanjh";

    // 配置资源id ,跟AuthorizationServerConfig.configure配置的resourceIds一样
    public static final String RESOURCE_ID = "resources-server-A";

    /*--------------------- JWT相关配置 ---------------------*/
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /*--------------------- JWT令牌校验工具 ---------------------*/
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        //设置JWT签名密钥
        jwtAccessTokenConverter.setSigningKey(SIGN_KEY);

        return jwtAccessTokenConverter;
    }

    /*--------------------- Zuul资源服务配置，针对认证服务器的配置 ---------------------*/

}
