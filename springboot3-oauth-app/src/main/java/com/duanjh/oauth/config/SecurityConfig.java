package com.duanjh.oauth.config;

import com.duanjh.oauth.service.OAuthUserDetailsService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 15:53
 * @Version: v1.0
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private JdbcTemplate template;

    @Resource
    private OAuthUserDetailsService userDetailsService;

    // OAuth2授权服务过滤器链（优先级最高）
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
        // 只匹配 /oauth2/** /.well-known/** ，不拦截 /doLogin /login
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .with(authorizationServerConfigurer, authServer -> authServer
                .authorizationService(jdbcAuthorizationService())
                .authorizationConsentService(jdbcConsentService())
                .registeredClientRepository(jdbcClientRepository())
                // 代表使用该端点默认配置，仅开启动态注册能力
                .oidc(Customizer.withDefaults())
            )
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/auth/login")))
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // 登录页面过滤器链
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/doLogin", "/auth/admin/client/**").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form ->
                        form.loginPage("/login")   // 自定义登录地址

                        .loginProcessingUrl("/doLogin")     // Security内置登录提交地址
                        .usernameParameter("username")
                        .passwordParameter("password")

                        .loginProcessingUrl("/auth/doLogin")    // 登录提交地址（无需自己写接口，Security自动处理）
                        .permitAll()
                        .failureUrl("/login?error=true") // 登录失败跳转回登录页带错误标识
                )
                .logout(LogoutConfigurer::permitAll)
                // 开发关闭csrf，生产建议开启
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    // ========== PostgreSQL持久化OAuth客户端、授权记录 ==========
    @Bean
    public RegisteredClientRepository jdbcClientRepository() {
        return new JdbcRegisteredClientRepository(template);
    }

    @Bean
    public JdbcOAuth2AuthorizationService jdbcAuthorizationService() {
        return new JdbcOAuth2AuthorizationService(template, jdbcClientRepository());
    }

    @Bean
    public JdbcOAuth2AuthorizationConsentService jdbcConsentService() {
        return new JdbcOAuth2AuthorizationConsentService(template, jdbcClientRepository());
    }

    // ========== 用户认证 ==========
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ========== JWT RSA签名密钥 ==========
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    // 发行者地址
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://127.0.0.1:9000/auth")
                .build();
    }

}
