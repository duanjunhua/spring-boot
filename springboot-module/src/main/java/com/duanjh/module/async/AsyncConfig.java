package com.duanjh.module.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-06-06 周四 17:28
 * @Version: v1.0
 * @Description: 自定义线程池配置
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置空闲线程数
        executor.setCorePoolSize(2);
        // 设置最大线程数
        executor.setMaxPoolSize(5);
        // 设置查询容量
        executor.setQueueCapacity(500);
        // 自定义线程名
        executor.setThreadNamePrefix("Personal-Thread-");
        // 线程初始化
        executor.initialize();
        return executor;
    }
}
