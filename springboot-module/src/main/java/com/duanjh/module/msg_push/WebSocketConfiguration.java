package com.duanjh.module.msg_push;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-05-29 周三 09:54
 * @Version: v1.0
 * @Description:
 */
//Websocket配置
@Configuration
public class WebSocketConfiguration {
    @Bean
    public ServerEndpointExporter serverEndpoint(){
        ServerEndpointExporter exporter = new ServerEndpointExporter();
        return exporter;

    }
}
