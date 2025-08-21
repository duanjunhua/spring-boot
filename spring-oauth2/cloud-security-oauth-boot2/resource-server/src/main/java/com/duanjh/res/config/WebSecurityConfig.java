package com.duanjh.res.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-20 周三 16:20
 * @Version: v1.0
 * @Description: 只有使用Zuul网关的统一鉴权方式才需要配置该类：根据自身情况做web安全配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 授权规则配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()   //屏蔽跨域防护
                // 对请求做授权处理
                .authorizeRequests()
                // 其他路径都要拦截
                .antMatchers("/**").permitAll();
    }
}
