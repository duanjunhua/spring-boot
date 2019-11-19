package com.webflux.hello.handle;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HelloHandler {
    public Mono<ServerResponse> helloHadnler(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .header("Context-Type", "text/plain; charset=utf-8")        //设置中文不乱码
                .body(BodyInserters.fromObject("Hello, Thanks use WebFlux ! 欢迎首次使用WebFlux"));
    }
}
