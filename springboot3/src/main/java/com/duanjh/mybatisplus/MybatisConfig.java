package com.duanjh.mybatisplus;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-06 周五 15:36
 * @Version: v1.0
 * @Description: 自定义配置，用于增加新配置
 */
@Configuration
public class MybatisConfig {

    // Mybatis个性化配置
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer() {
            // 对应mybatisconfig.xml的settings扩展点
            @Override
            public void customize(MybatisConfiguration configuration) {
                // 开启驼峰命名
                configuration.setMapUnderscoreToCamelCase(true);
                // 允许缓存
                configuration.setCacheEnabled(true);
            }
        };
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }
}
