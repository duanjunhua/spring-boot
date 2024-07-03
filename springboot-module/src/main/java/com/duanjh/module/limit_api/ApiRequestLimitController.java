package com.duanjh.module.limit_api;

import com.duanjh.module.aop.Limiting;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-07-03 周三
 * @Version: v1.0
 * @Description: API接口请求频率限制
 */
@Slf4j
@RestController
@RequestMapping("/api_limiter")
public class ApiRequestLimitController {

    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();


    /**------------------------- 方式一：RateLimiter -------------------------*/
    /**
     * @param uniqueKey: 请求唯一标识
     */
    @GetMapping("/limit-openapi")
    public String limitOpenApi(@RequestParam String uniqueKey){
        RateLimiter rateLimiter = limiters.computeIfAbsent(uniqueKey, k -> RateLimiter.create(1.0 / 30));  // 每30秒访问一次
        if(!rateLimiter.tryAcquire()){
            return "Too many requests, Please try again later";
        }

        // 执行业务逻辑
        return "OK";
    }

    // 使用自定义注解的限流方式
    @GetMapping("/limit-aop")
    @Limiting(limtTimes = 1, name = "limit-aop")
    public String limitAop(){
        return "OK";
    }

    /**------------------------- 方式二：使用Redis缓存 -------------------------*/
}
