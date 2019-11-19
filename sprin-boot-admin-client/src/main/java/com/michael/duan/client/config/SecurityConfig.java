package com.michael.duan.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author : Michael J H Duan
 * @version v1.0
 * @date Date : 2019-08-02 16:34
 */

//Make the actuator endpoints accessible
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //For the sake of brevity we’re disabling the security for now. Have a look at the security section on how to deal with secured endpoints.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
    }
}
