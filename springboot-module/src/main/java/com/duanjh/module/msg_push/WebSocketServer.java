package com.duanjh.module.msg_push;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-05-29 周三 09:40
 * @Version: v1.0
 * @Description: Websocket Message
 */
@Slf4j
@Component
@ServerEndpoint("/ws/connect")  //类似restful中的@RequestMapping
public class WebSocketServer {

    public static ConcurrentHashMap<String, Session> clientMap = new ConcurrentHashMap<String,Session>();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token){
        log.info("客户端开始建立连接：{}", token);
        clientMap.put(token, session);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("token") String token){
        log.info("客户端：{} 发送给服务器的消息：{}", token, message);
    }

    @OnClose
    public void onClose(@PathParam("token") String token){
        log.info("客户端关闭连接：{}", token);
        clientMap.remove(token);
    }

    @OnError
    public void onError(Throwable throwable){
        log.error("连接发生异常:{}", throwable.getLocalizedMessage());
    }

}
