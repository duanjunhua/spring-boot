package com.duanjh.auth.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-08 周五 15:41
 * @Version: v1.0
 * @Description: Security配置
 */
@Configuration
@EnableWebSecurity(debug = false)   // 开启WebSecurity服务
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    /**
     * 密码编码器
     * 可自定义密码加密算法
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 授权规则配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                // 屏蔽跨域防护
                .disable()
                // 对请求做授权处理
                .authorizeRequests()
                // 登录路径放行
                .antMatchers("/login").permitAll()
                // 其他路径都要拦截
                .anyRequest().authenticated()
                // 允许表单登录
                .and().formLogin()
                /*
                //  设置登陆页
                .loginPage("/login.html")
                // 登录处理地址
                .loginProcessingUrl("/login")
                // 登录成功地址
                .successForwardUrl("/home").permitAll()
                 */
                //登出
                .and().logout()
                // .logoutUrl("/logout")
                .permitAll();
    }

    /**
     * 配置认证管理器，授权模式为"poassword"时会用到：
     * 后续Oauth2支持password模式需要做认证时会用到认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
