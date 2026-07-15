package com.kingroad.pulsar.config;

import com.kingroad.pulsar.auth.interceptor.InitBootInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:41
 * @Version: v1.0
 * @Description: MVC配置注册拦截器
 */
@Configuration
public class OauthWebMvcConfig implements WebMvcConfigurer {

    @Resource
    private InitBootInterceptor initInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initInterceptor).addPathPatterns("/**");
    }
}
