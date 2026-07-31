package com.kingroad.pulsar.authorization;

import com.kingroad.pulsar.authorization.sso.OAuth2UserService;
import com.kingroad.pulsar.authorization.sso.OAuthOidcUserService;
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
    OAuth2UserService oAuth2UserService;    // 通过SSO 服务/userinfo 端点获取用户信息
    @Resource
    OAuthOidcUserService oidcUserService;   // 通过id_token解析用户信息，与oAuth2UserService二选一即可
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
                    .requestMatchers("/login", "/login/auth/**", "/doLogin","/css/**", "/element/**", "/js/**").permitAll()
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

                // jwt模式从id_token获取用户信息
                .userInfoEndpoint(u -> u.oidcUserService(oidcUserService))

                // 配置从/userinfo获取用户信息
                //.userInfoEndpoint(u -> u.userService(oAuth2UserService))
                .defaultSuccessUrl("/index", true)
            )
            .logout(logout -> logout
                // 自定义登出请求地址，前端访问 /logout 触发登出
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                // 1. 销毁当前Session
                .invalidateHttpSession(true)
                // 2. 清除Security上下文（SecurityContextHolder）
                .clearAuthentication(true)
                // 3. 删除Cookie，可添加多个（JSESSIONID、记住我cookie等）
                .deleteCookies("JSESSIONID")
                // 放行登出接口，无需认证即可访问
                .permitAll()
            );
        return http.build();
    }
}
