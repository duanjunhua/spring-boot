package com.duanjh.flux;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-02 周一 16:59
 * @Version: v1.0
 * @Description: 响应式编程
 */
@RestController
@RequestMapping("/flux")
public class FluxController {

    /**
     * 响应式编程的返回值必须是 Flux 或者 Mon
     */
    @GetMapping("/mono")
    public Mono<String> flux(){
        return Mono.just("这是一段基于Reactive的流式编程");
    }

    String[] stream_data = {"这","是","一","段","基","于","reactive","的","流","式","编","程"};
    /**
     * 配置response避免中文乱码
     */
    @GetMapping(value = "/stream")
    public Flux<String> stream(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/event-stream;charset=UTF-8");
        return Flux
                // 每秒1次
                .interval(Duration.ofSeconds(1))
                // 每次产生的数据
                .map(time -> stream_data[Math.toIntExact(time)])
                // 执行次数
                .take(stream_data.length);

    }
}
