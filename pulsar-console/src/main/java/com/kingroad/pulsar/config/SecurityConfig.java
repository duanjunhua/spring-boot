package com.kingroad.pulsar.config;

import cn.hutool.core.util.ObjectUtil;
import com.kingroad.pulsar.auth.encoder.Md5PasswordEncoder;
import com.kingroad.pulsar.auth.service.*;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

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
    private SysConfigService configService;

    @Resource
    SsoOAuth2UserService ssoOAuth2UserService;

    @Resource
    SsoOidcUserServiceImpl oidcUserService;

    @Resource
    SsoClientRegistrationRepository clientRegistrationRepository;

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * 动态读取OAuth开关
         */
        boolean oauthOpen = ObjectUtil.isNull(configService.getConfigValue(CommonConst.SSO_ENABLE)) ? false : Boolean.parseBoolean(configService.getConfigValue(CommonConst.SSO_ENABLE));

        http
            // 使用 Spring Security 的配置方式设置 X-Frame-Options 为 SAMEORIGIN
            .headers(httpSecurityHeadersConfigurer -> {
                    httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig ->  {
                        frameOptionsConfig.sameOrigin();
                    });
                })
            .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 本地登录放行
                        .requestMatchers("/oauth2/authorization/**").permitAll()
                        .requestMatchers("/login/oauth2/code/**").permitAll()
                        .requestMatchers("/init/**","/static/**").permitAll()
                        // 其余接口必须认证
                        .anyRequest().authenticated()
                )

                // 表单登录入口
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/doLogin")
                        .successHandler(loginSuccessHandler())
                        .permitAll()
                );

        /**
         * 启用OAuth2登录，普通用户强制走SSO
         */
        if(oauthOpen) {
            http.oauth2Login(oauth -> oauth
                    .clientRegistrationRepository(clientRegistrationRepository)
                    .userInfoEndpoint(userInfo -> userInfo.userService(ssoOAuth2UserService))
//                    .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService))
                    .loginPage("/login")
                    // OAuth2登录复用统一成功处理器，统一处理session、跳转逻辑
                    .successHandler(loginSuccessHandler())
            );
        }

        http.logout(logout -> logout.logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        );
        return http.build();
    }

    // 全局统一登录成功处理器：表单登录、OIDC第三方登录共用，强制跳/index
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler("/welcome");
        handler.setAlwaysUseDefaultTargetUrl(true); // 强制忽略缓存请求，永远跳欢迎页
        return handler;
    }
}
