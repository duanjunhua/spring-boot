package com.michael.duan.server.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author : Michael J H Duan
 * @version v1.0
 * @date Date : 2019-08-02 17:00
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String adminContextPath;

    public SecurityConfig(AdminServerProperties properties){
        this.adminContextPath = properties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setTargetUrlParameter("redirectTo");
        handler.setDefaultTargetUrl(adminContextPath + "/");

        http.authorizeRequests().antMatchers(
                adminContextPath + "/assets/*",
                adminContextPath + "/login"
        ).permitAll().anyRequest().authenticated()
        .and()
        .formLogin().loginPage(adminContextPath + "/login").successHandler(handler)
        .and()
        .logout().logoutUrl(adminContextPath + "/logout")
        .and()
        .httpBasic().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringAntMatchers(
                "/instances",
                "actuator",
                adminContextPath + "/logout"
        );
    }
}
