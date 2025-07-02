package com.webflux.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-02 周三 14:48
 * @Version: v1.0
 * @Description: 流式处理器
 */
@RestController
@RequestMapping("/flux")
public class FluxController {

    // 异步：http://localhost:8085/flux 或者 http://localhost:8085/flux?welcomeMsg=Michael
    @GetMapping
    public Mono<String> hello(@RequestParam(defaultValue = "world") String welcomeMsg) {
        return Mono.just("Hello, " + welcomeMsg + "!");
    }

    // 异步流：http://localhost:8085/flux/stream
    @GetMapping("/stream")
    public Flux<String> streamMessage() {
        return Flux
                // 每秒1次
                .interval(Duration.ofSeconds(1))
                // 每次产生的数据
                .map(t -> "Message: " + t)
                // 执行5次
                .take(5);
    }
}
