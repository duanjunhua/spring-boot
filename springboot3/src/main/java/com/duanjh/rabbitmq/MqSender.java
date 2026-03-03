package com.duanjh.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-09 周一 15:18
 * @Version: v1.0
 * @Description: 发送者
 */
@Slf4j
@Component
public class MqSender {

    public static final String DEFAULT_TOPIC = "default";
    public static final String DEFAULT_EXCHANGE = "";

    @Autowired
    AmqpTemplate template;

    public void defaultTopicSend(String message){
        dynamicTopicSend(DEFAULT_EXCHANGE, DEFAULT_TOPIC, message);
    }

    public void dynamicTopicSend(String exchage, String topic, Object message){
        log.info("RabbitMQ发送的消息：{}", message);
        this.template.convertAndSend(exchage, topic, message);
    }

}
