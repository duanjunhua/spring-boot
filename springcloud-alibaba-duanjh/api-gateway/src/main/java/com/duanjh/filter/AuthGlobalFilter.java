package com.duanjh.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-27
 * @Version: V1.0
 * @Description: 自定义全局拦截器：需要实现GlobalFilter和Ordered
 */
@Slf4j
// @Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    //完成判断逻辑
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取token信息
        String token = exchange.getRequest().getQueryParams().getFirst("token");

        //token不存在
        if(StringUtils.isBlank(token)){
            log.warn("鉴权失败，token不存在");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //调用chain.filter递归拦截器链继续向下执行
        return chain.filter(exchange);
    }

    //顺序，数值越小，优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
