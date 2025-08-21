package com.duanjh.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-20 周三 15:48
 * @Version: v1.0
 * @Description: 配置了资源服务器之后，WebSecurity直接放行即可，所有的鉴权都交给资源服务配置
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //针对于zuul本身的请求直接放行，当访问某个资源的时候会通过oauth2检查权限
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and().csrf().disable();
    }
}
