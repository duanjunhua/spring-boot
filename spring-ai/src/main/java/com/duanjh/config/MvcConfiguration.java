package com.duanjh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-01-30 周五 16:42
 * @Version: v1.0
 * @Description:
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    /**
     * 解决跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
