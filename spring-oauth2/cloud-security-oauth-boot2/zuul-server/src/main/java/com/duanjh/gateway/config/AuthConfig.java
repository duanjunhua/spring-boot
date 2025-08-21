package com.duanjh.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-20 周三 15:03
 * @Version: v1.0
 * @Description: Zuul资源服务配置，针对认证服务器的配置
 */
@Configuration
@EnableResourceServer
public class AuthConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    /**
     * 资源服务安全性配置
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId(ResourceServerConfig.RESOURCE_ID)
                .tokenStore(tokenStore)
                .stateless(Boolean.TRUE);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 针对于直接放行的资源
        http.authorizeRequests().antMatchers("/**").permitAll();
    }

}
