package com.duanjh.mvcfn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-05 周四 16:37
 * @Version: v1.0
 * @Description: 定义路由模型
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class RoutingConfiguration {

    private static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);

    @Bean
    public RouterFunction<ServerResponse> routerFunction(BootUserWebMvcHandler handler) {
        return route()
                .GET("/route/list", ACCEPT_JSON, handler::listUsers)
                .GET("/route/find/{username}", ACCEPT_JSON, handler::findUser)
                .build();
    }
}
