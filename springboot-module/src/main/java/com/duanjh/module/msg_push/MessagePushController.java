package com.duanjh.module.msg_push;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-05-28 周二 16:54
 * @Version: v1.0
 * @Description: 消息实时推送
 */
@Slf4j
@RestController
@RequestMapping("/msg_push")
public class MessagePushController {

    // 方式一：短轮询，问题：有效请求少，请求大时服务器压力大，资源浪费
    /**
     * 前端使用定时任务间隔发送请求
     * setInteval(() =>{
     *      console.log("执行业务处理")
     * }, 1000) 每秒执行1次
     */

    // 方式二：长轮询：当前端发起一个请求给后端的时候，后端不会立马的响应而是将当前的请求线程进行挂起，只有当出现最新的消息才会响应给前端
    /**
     * 可减少无效请求
     * 问题：
     *  （1）等待时间长，请求超时
     *  （2）高并发下，易造成线程堆积问题，服务器压力大
     */

    // 方式三：SSE推送数据
    /**
     * 1、客户端请求建立长链接
     * 2、服务端在响应中设置SSE长链接协议，建立长链接成功：response.setContentType("text/event-stream")
     * 3、服务端在有数据时推送数据
     *
     * SSE订阅服务器事件时候，常见的Header设置:
     *      Accept: text/event-stream 表示可接收事件流类型
     *      Cache-Control: no-cache 禁用任何的事件缓存
     *      Connection: keep-alive 表示正在使用持久连接
     *
     *
     */
    public Map<String, SseEmitter> sseCache = new HashMap();
    public SseEmitter connect(){
        final String clientId = UUID.randomUUID().toString().replace("-", "");
        SseEmitter sseEmitter = new SseEmitter(0L);

        try {
            sseEmitter.send(SseEmitter.event().comment("创建连接成功..."));
        } catch (IOException e) {
           log.error("创建连接失败，{}...", e.getLocalizedMessage());
        }

        sseEmitter.onCompletion(() -> {
            log.info("结束连接：{} ....", clientId);
            //移除客户端
        });

        sseEmitter.onTimeout(() -> {
            log.warn("连接超时：{} .....", clientId);
            //移除客户端
        });

//        sseEmitter.onError(() -> {
//            log.error("连接异常：{} ....", clientId);
//            //移除客户端
//        });

        sseCache.put(clientId, sseEmitter);
        return sseEmitter;
    }

    // 方式四：WebSocket
    /**
     * 1、客户端请求建立WS连接
     * 2、客户端与服务端相互发送数据
     * 3、客户端请求断开连接
     * 4、客户端请求建立长链接的请求：
     *      new WebSocket("ws://127.0.0.1:8080/ws/connect")
     *
     *  WebSocketConfiguration、WebSocketServer
     */

    // 方式五：netty实时推送
    /**
     * 基于netty框架实施通信业务以及并发量高的复杂场景，因为Netty已经集成了NIO模型以及对Websocket进行了封装，使得使用Websocket更加的方便，性能也是更加的高
     */

    // 方式六：MQTT实现实时消息推送
    /**
     * 基于发布-订阅模式的消息传输协议，适用于资源受限的设备和低带宽、高延迟或不稳定的网络环境
     * QoS 0：消息最多传送一次。如果当前客户端不可用，它将丢失这条消息
     * Qos 1：消息至少传送一次
     * Qos 2：消息只传送一次
     */






}
