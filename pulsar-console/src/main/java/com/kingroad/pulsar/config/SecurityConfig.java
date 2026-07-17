package com.kingroad.pulsar.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kingroad.pulsar.auth.encoder.Md5PasswordEncoder;
import com.kingroad.pulsar.auth.filter.OauthSkipFilter;
import com.kingroad.pulsar.auth.handler.LoginSuccessHandler;
import com.kingroad.pulsar.auth.service.PulsarUserDetailsService;
import com.kingroad.pulsar.constant.CommonConst;
import com.kingroad.pulsar.entity.uo.SysUser;
import com.kingroad.pulsar.service.config.SysConfigService;
import com.kingroad.pulsar.service.uo.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private PulsarUserDetailsService userDetailsService;
    @Resource
    private OauthSkipFilter oauthSkipFilter;
    @Resource
    private SysConfigService configService;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    @Resource
    private SysUserService sysUserService;

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
            http.oauth2Login(oauth -> oauth
                    .loginPage("/login")
                    .clientRegistrationRepository(clientRegistrationRepository())
                    .userInfoEndpoint(userInfo -> userInfo.userService(oauthUserService()))
                    // OAuth2登录复用统一成功处理器，统一处理session、跳转逻辑
                    .successHandler(loginSuccessHandler)
            );
            // 自定义过滤器：超级管理员账号登录后跳过OAuth二次校验
            http.addFilterBefore(oauthSkipFilter, UsernamePasswordAuthenticationFilter.class);
        }

        http.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"));
        return http.build();
    }

    // 动态构建OAuth客户端注册信息（从数据库读取配置）
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId(applicationName)
                .clientId(configService.getConfigValue(CommonConst.OAUTH_CLIENT_ID))
                .clientSecret(configService.getConfigValue(CommonConst.OAUTH_CLIENT_SECRET))
                .authorizationUri(configService.getConfigValue(CommonConst.OAUTH_AUTHORIZATION_URI))
                .tokenUri(configService.getConfigValue(CommonConst.OAUTH_TOKEN_URI))
                .userInfoUri(configService.getConfigValue(CommonConst.OAUTH_USER_INFO_URI))
                .userNameAttributeName("sub")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(configService.getConfigValue(CommonConst.OAUTH_REDIRECT_URI))
                .build();
        return new InMemoryClientRegistrationRepository(registration);
    }

    // OAuth用户自动创建普通用户（userType=OAUTH）
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService() {
//        return request -> {
//            OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(request);
//            String unionId = oAuth2User.getAttribute("sub");
//            LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
//            wrapper.eq(SysUser::getUserId, unionId);
//            SysUser exist = sysUserService.getOne(wrapper);
//
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            // 不存在则新建第三方用户
//            if (exist == null) {
//                SysUser newUser = new SysUser();
//                newUser.setUsername("oauth_" + unionId);
//                newUser.setUserId(unionId);
//                newUser.setPulsarClusterId(1);
//                newUser.setTenantName("zevent");
//                newUser.setIsSuperAdmin(Boolean.FALSE);
//                newUser.setEnable(true);
//                sysUserService.save(newUser);
//                authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//            }
//            // 包装带权限的OAuth2User，替换原生无权限对象
//            return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "sub");
//        };
        // 区分OIDC与普通OAuth2
        return request -> {
            // 如果是OIDC Provider，用OidcUserService加载
            if (request.getClientRegistration().getProviderDetails().getJwkSetUri() != null) {
                OidcUserService oidcService = new OidcUserService();
                OidcUser oidcUser = oidcService.loadUser((OidcUserRequest)request);
                String unionId = oidcUser.getSubject();
                // 复用你原有新建用户逻辑
                return handleOauthUser(unionId, oidcUser.getAttributes());
            } else {
                OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(request);
                String unionId = oAuth2User.getAttribute("sub");
                return handleOauthUser(unionId, oAuth2User.getAttributes());
            }
        };
    }

    // 抽离公共用户创建逻辑
    private OAuth2User handleOauthUser(String unionId, Map<String,Object> attrs) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUserId, unionId);
        SysUser exist = sysUserService.getOne(wrapper);
        if (exist == null) {
            SysUser newUser = new SysUser();
            newUser.setUsername("oauth_" + unionId);
            newUser.setUserId(unionId);
            newUser.setPulsarClusterId(1);
            newUser.setTenantName("zevent");
            newUser.setIsSuperAdmin(Boolean.FALSE);
            newUser.setEnable(true);
            sysUserService.save(newUser);
        }
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new DefaultOAuth2User(authorities, attrs, "sub");
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
