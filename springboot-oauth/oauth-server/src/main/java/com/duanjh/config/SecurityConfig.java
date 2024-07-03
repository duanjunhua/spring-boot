package com.duanjh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: Michael J H Duan[Junhua Duan]
 * @Date: 2023-05-15 16:53
 * @Version: V1.0
 * @Description: 配置加密方式、配置用户、配置认证管理器、配置安全拦截策略
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 指定密码的加密方式
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        // 使用BCrypt强哈希函数加密方案（密钥迭代次数默认为10）
        return new BCryptPasswordEncoder();
    }


    /**
     * 配置用户
     */
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        //TODO: 从数据库查询用户
        builder.inMemoryAuthentication()
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123")).roles("admin")
                .and()
                .withUser("user").password(new BCryptPasswordEncoder().encode("123")).roles("user");
    }

    /**
     * 配置认证管理器
     * AuthenticationManager对象在OAuth2.0认证服务中使用，提前存入IOC容器中
     * Oauth的密码模式需要
     */

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManagerBean();
    }

    /**
     * 配置安全拦截策略
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO:允许表单登录
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login").permitAll()
                .and()
                .csrf().disable();
    }
}
