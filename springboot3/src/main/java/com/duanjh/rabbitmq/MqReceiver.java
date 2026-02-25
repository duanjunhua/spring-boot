package com.duanjh.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-09 周一 15:20
 * @Version: v1.0
 * @Description: MQ接收者
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MqReceiver {

    AmqpTemplate template;

    /**
     * 方式一：直接绑定TOPIC
     */
    @RabbitHandler
    @RabbitListener(queues = MqSender.DEFAULT_TOPIC)
    public void process(String message) {
        log.info("RabbitMQ默认TOPIC接收的消息：{}",message);
    }

    /**
     * 方式二动态获取TOPIC
     */
    public Object processWithTopic(String topic) {
        Object msg = template.receiveAndConvert(topic);
        log.info("动态指定TOPIC：{}，获取的消息：{}", topic, msg);
        return msg;
    }
}
