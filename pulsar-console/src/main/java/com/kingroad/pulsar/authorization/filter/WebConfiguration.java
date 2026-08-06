package com.kingroad.pulsar.authorization.filter;

import com.kingroad.pulsar.authorization.handler.SystemInitInterceptor;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:06
 * @Version: v1.0
 * @Description: 自定义Filter，可实现记录调用日志、排除XSS威胁字符、执行验证权限等
 */
@Slf4j
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Resource
    SystemInitInterceptor initInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initInterceptor)
                .addPathPatterns("/**")
                // 排除静态资源
                .excludePathPatterns("/element/**","/js/**","/css/**","/images/**")
                // 初始化页面本身不需要登录拦截也要放开
                .excludePathPatterns("/system/init","/init/save", "/rsa/get-pubkey");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 放行静态资源
        registry.addResourceHandler("/element/**")
                .addResourceLocations("classpath:/static/element/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }

    @Bean
    public RemoteIpFilter remoteIpFilter(){
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean<Filter> filterRegistration(){
        return buildFilter(new ApplicationRequestFilter(), ApplicationRequestFilter.class.getSimpleName(), 1, "/**");
    }

    // 抽取公共构建方法
    private FilterRegistrationBean<Filter> buildFilter(Filter filter, String name, int order, String... patterns) {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(filter);
        bean.setName(name);
        bean.addUrlPatterns(patterns);
        bean.setOrder(order);
        return bean;
    }

}
