package com.kingroad.pulsar.config;

import com.kingroad.pulsar.auth.encoder.Md5PasswordEncoder;
import com.kingroad.pulsar.auth.filter.OauthSkipFilter;
import com.kingroad.pulsar.auth.handler.LoginSuccessHandler;
import com.kingroad.pulsar.auth.service.PulsarUserDetailsService;
import com.kingroad.pulsar.constant.CommonConst;
import com.kingroad.pulsar.service.config.SysConfigService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:35
 * @Version: v1.0
 * @Description: 核心安全配置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Resource
    private PulsarUserDetailsService userDetailsService;
    @Resource
    private OauthSkipFilter oauthSkipFilter;
    @Resource
    private SysConfigService configService;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * 动态读取OAuth开关
         */
        boolean oauthOpen = CommonConst.FLAG_ONE.equals(configService.getConfigValue("oauth_enable"));

        http
            // 使用 Spring Security 的配置方式设置 X-Frame-Options 为 SAMEORIGIN
            .headers(httpSecurityHeadersConfigurer -> {
                    httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig ->  {
                        frameOptionsConfig.sameOrigin();
                    });
                })
            .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/init/**","/static/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 表单登录入口
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/doLogin")
                        .successHandler(loginSuccessHandler)
                        .defaultSuccessUrl("/welcome", true)
                        .permitAll()
                );

        /**
         * 启用OAuth2登录，普通用户强制走SSO
         */
        if(oauthOpen) {
            http.oauth2Login(oauth -> oauth.loginPage("/login"));

            // 自定义过滤器：超级管理员账号登录后跳过OAuth二次校验
            http.addFilterBefore(oauthSkipFilter, UsernamePasswordAuthenticationFilter.class);
        }

        http.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"));
        return http.build();
    }

    @Bean
    @Primary    // 表示使用自定义的PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
