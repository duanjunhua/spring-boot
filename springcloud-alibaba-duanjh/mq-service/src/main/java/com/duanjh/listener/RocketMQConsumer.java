package com.duanjh.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-29
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "topicTest", consumerGroup = "consumerTest")
public class RocketMQConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg) {
        log.info("Comsumer consume message: {}, type: {}", msg, msg.getClass());
    }
}
