package com.webflux.hello.config;

import com.webflux.hello.handle.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class WebFluxConfig {

    @Bean
    public RouterFunction<ServerResponse> helloWebFlux(HelloHandler handler){
        return RouterFunctions.route(RequestPredicates.GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::helloHadnler);
    }
}
