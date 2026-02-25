package com.duanjh.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-09 周一 15:16
 * @Version: v1.0
 * @Description: MQ配置
 */
@Configuration
public class RabbitmqConfig {

    public static final String DEFAULT_QUEUE = "queue";

    @Bean
    public Queue queue(){
        // 表示创建默认的队列
        return new Queue(DEFAULT_QUEUE);
    }

    /**
     * 指定发送对象消息序列化，解决java.lang.SecurityException: Attempt to deserialize unauthorized class xxx
     *      set environment variable 'SPRING_AMQP_DESERIALIZATION_TRUST_ALL' or system property 'spring.amqp.deserialization.trust.all' to true
     * 问题
     */
    @Bean
    public MessageConverter messageConverter(){
        DefaultClassMapper mapper = new DefaultClassMapper();
        mapper.setTrustedPackages("com.duanjh.jpa.entity");
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(mapper);
        return converter;
    }
}
