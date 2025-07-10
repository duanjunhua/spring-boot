package com.duanjh.oauth2.security.config;

import com.duanjh.oauth2.security.entity.Permission;
import com.duanjh.oauth2.security.handler.PersonalAuthorizationFailureHandler;
import com.duanjh.oauth2.security.handler.PersonalizationLogoutHandler;
import com.duanjh.oauth2.security.handler.PersonalizationSuccessLogoutHandler;
import com.duanjh.oauth2.security.repo.PermissionRepository;
import com.duanjh.oauth2.security.service.UserDetailServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-02 周三 17:26
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
//        securedEnabled=true,    // 启用@Secured
        prePostEnabled = true   // 启用@PreAuthorize
)
public class WebSecurityConfig  {

    // 方式二Permission
    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    ApplicationContext context;

    @Autowired
    UserDetailsService userDetailService;

    // 授权规则配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* -------------------------- 方式一：用内存模拟 -------------------------- */
        /*
        return http.authorizeHttpRequests(request -> {
                    request.requestMatchers("/login").permitAll()      // 登录路径放行
                            .anyRequest().authenticated();                        // 其他路径都要认证后才能访问
                }).formLogin(formLogin -> formLogin.successForwardUrl("/security/loginSuccess"))    // 允许表单登录并设置登良路成功页
                .logout(logout -> logout.permitAll())                           // 登出路径放行
                .csrf(csrf -> csrf.disable())                                   // 甘比跨域伪造检查
                .build();
         */

        /* -------------------------- 方式二 -------------------------- */
        // 动态添加授权:从数据库动态查询出，哪些资源需要什么样的权限
        List<Permission> permissions = permissionRepository.findPermissionLst();

        return http.formLogin(formLogin -> {
                    // 登录页面地址
                    formLogin.loginPage("/login.html");
                    // 登录处理地址
                    formLogin.loginProcessingUrl("/login");
                    formLogin.permitAll();

                    /*--------------------- 返回方式二选一 -------------------------*/
                    // 设置登录成功页，如果使用的是自定义AuthenticationSuccessHandler返回json格式，需要将这个注释掉
                    formLogin.defaultSuccessUrl("/home");

                    // 自定义AuthenticationSuccessHandler处理器处理返回JSON数据
                    //formLogin.successHandler(new PersonalAuthorizationHandler());
                    //formLogin.failureHandler(new PersonalAuthorizationFailureHandler());
                })
                .logout(logout -> {
                    // 自定义登出路径
                    logout.logoutUrl("/logout");
                    // 登出路径放行
                    logout.permitAll();
                    // 登出后session无效
                    logout.invalidateHttpSession(true);

                    // 如有需要，可以配置登出处理器
                    logout.addLogoutHandler(new PersonalizationLogoutHandler());

                    // 如有需要，可以配置登出成功处理器
                    logout.logoutSuccessHandler(new PersonalizationSuccessLogoutHandler());

                })
                // 关闭跨域伪造检查
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(request -> {

                    // 授权方式一：Web授权
                    if(CollectionUtils.isNotEmpty(permissions)){
                        // 权限
                        permissions.stream().forEach(p -> {
                            request.requestMatchers(p.getResource()).hasAuthority(p.getExpression());
                        });
                    }
                    request.anyRequest().authenticated();
                })

                // 前后端分离时可使用，此处直接先重定向到登录页
                .exceptionHandling(resolver -> {
                    resolver.accessDeniedHandler(new AccessDeniedHandler() {
                        @Override
                        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                            log.info("此处写解决认证过的用户访问拒绝访问，此处设置重定向到登录1");
                            response.sendRedirect("/login.html");
                        }
                    }).authenticationEntryPoint(new AuthenticationEntryPoint() {
                        @Override
                        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                            log.info("此处写解决认证过的用户访问无权限资源时的异常的处理，此处设置重定向到登录");
                            response.sendRedirect("/login.html");
                        }
                    });
                })

                // 配置Remember Me
                .rememberMe(rmc -> {
                    // 持久化
                    rmc.tokenRepository(persistentTokenRepository())
                        // 过期时间，设置3600秒，即1小时
                        .tokenValiditySeconds(3600)
                        // 用于加载用户认证信息
                        .userDetailsService(userDetailService);

                })

                .build();
    }

    /* -------------------------- 方式一：用内存模拟 -------------------------- */
    /*
    // 密码编码器
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 设置不加密
        return NoOpPasswordEncoder.getInstance();
    }

    // 提供用户信息，实际需要实现UserDetailsService自定义处理类从数据库查询
    @Bean
    public UserDetailsService userDetailsService(){
        // 用内存模拟
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("duanjh").password("123456").authorities("admin").build());
        return manager;
    }
     */

    /* -------------------------- 方式二：数据库方案 -------------------------- */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /* -------------------------- Remember Me功能配置 -------------------------- */

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(context.getBean(DataSource.class));
        // 启动创建表persistent_logs表，首次启动时可设置true，后面设置false
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
