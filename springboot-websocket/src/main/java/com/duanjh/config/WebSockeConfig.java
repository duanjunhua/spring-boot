package com.duanjh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSockeConfig {

    /**
     * 注入一个ServerEndpointExporter,该Bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint
     */
    @Bean
    ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
