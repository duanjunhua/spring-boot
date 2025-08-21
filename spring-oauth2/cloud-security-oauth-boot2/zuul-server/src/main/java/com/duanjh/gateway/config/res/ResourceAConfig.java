package com.duanjh.gateway.config.res;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-20 周三 15:06
 * @Version: v1.0
 * @Description: 微服务，资源服务resource-server-A配置， 如果有其他的资源服务还需要配置其他的ResourceConfig
 */
@Configuration
@EnableResourceServer
public class ResourceAConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "resources-server-A";

    @Autowired
    private TokenStore tokenStore;

    /**
     * 资源服务安全性配置
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId(RESOURCE_ID)
                .tokenStore(tokenStore)
                .stateless(Boolean.TRUE);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //如果是其他微服务资源需要oauth2 认证
        http.authorizeRequests()

                //校验scope必须为all , 针对于/resource/路径的请求需要oauth2验证有ROLE_API的权限才能访问
                .antMatchers("/services/resources-server-A/**").access("#oauth2.hasScope('resources-server-A')")

                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .and().cors().and().csrf().disable();
    }
}
