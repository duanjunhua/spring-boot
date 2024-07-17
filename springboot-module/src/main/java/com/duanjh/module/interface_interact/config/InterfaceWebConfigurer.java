package com.duanjh.module.interface_interact.config;

import com.duanjh.module.interface_interact.interceptor.ResResultInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: Michale J H Duan[JunHua]
 * @Date: 2024-07-16 星期二 17:38
 * @Version: v1.0
 * @Description: 注册自定义拦截器
 */
@Configuration
public class InterfaceWebConfigurer extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResResultInterceptor()).addPathPatterns("/**");
    }
}
