package com.kingroad.pulsar.authorization;

import com.kingroad.pulsar.authorization.sso.OAuth2UserService;
import com.kingroad.pulsar.authorization.sso.SsoClientRegistrationRepository;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 11:22
 * @Version: v1.0
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    LocalUserDetailService localUserDetailService;

    // 第三方SSO登录
    @Resource
    OAuth2UserService oAuth2UserService;
    @Resource
    SsoClientRegistrationRepository ssoClientRegistrationRepository;

    /**
     * 密码加密器（必须配置）
     */
    @Bean
    @Primary    // 表示使用自定义的PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    /**
     * 认证提供者：绑定自定义UserDetailsService + 密码编码器，适配本地账号密码校验
     */

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(localUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        // 隐藏用户不存在异常
        provider.setHideUserNotFoundExceptions(false);

        return provider;
    }

    // 安全过滤链
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    // 放行自定义登录页面、静态资源(css/js/img)、验证码等
                    .requestMatchers("/login", "/doLogin","/css/**", "/element/**", "/js/**").permitAll()
                    // 其余所有接口必须登录认证
                    .anyRequest().authenticated()
            )
            // 本地用户登录
            .formLogin(form ->
                    // 访问该地址：控制器跳转自定义登录html页面
                    form.loginPage("/login")
                        // 表单提交地址：Security内置接口，不用自己写Controller接收
                        .loginProcessingUrl("/doLogin")
                        .defaultSuccessUrl("/index", true)
                        // 登录失败跳转回登录页，携带错误参数
                        .failureUrl("/login?error=true")
                        .permitAll()
            )
            // 第三方用户SSO登录
            .oauth2Login(oauth2 -> oauth2
                    .loginPage("/login")
                    .clientRegistrationRepository(ssoClientRegistrationRepository)
                    .userInfoEndpoint(u -> u.userService(oAuth2UserService))
                    .defaultSuccessUrl("/index", true)
            );
        return http.build();
    }
}
