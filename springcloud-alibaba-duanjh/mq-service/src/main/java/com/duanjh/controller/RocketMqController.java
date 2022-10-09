package com.duanjh.controller;

import com.fasterxml.jackson.core.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-29
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/mq")
public class RocketMqController {

    RocketMQTemplate mqTemplate;

    @Autowired
    public void setMqTemplate(RocketMQTemplate mqTemplate) {
        this.mqTemplate = mqTemplate;
    }

    @GetMapping("/send-msg/{msg}")
    public String send(@PathVariable("msg") String msg) throws Exception {

        mqTemplate.convertAndSend("topicTest", msg);
        return "发送成功";

    }

}
