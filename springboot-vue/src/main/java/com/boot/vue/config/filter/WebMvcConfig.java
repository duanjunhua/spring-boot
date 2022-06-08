package com.boot.vue.config.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-08
 * @Version: V1.0
 * @Description: 配置拦截器类
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    AuthenticationFilter authenticationFilter;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry
            //添加拦截器
            .addInterceptor(authenticationFilter)
            //拦截所有请求
            .addPathPatterns("/**")
            //登录请求排除不拦截
            .excludePathPatterns("/user/login");
    }
}
