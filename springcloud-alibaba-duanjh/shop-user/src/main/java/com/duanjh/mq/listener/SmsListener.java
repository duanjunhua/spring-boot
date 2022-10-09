package com.duanjh.mq.listener;

import com.alibaba.fastjson.JSON;
import com.duanjh.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-10-09
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "shop-user", topic = "order-topic")
public class SmsListener implements RocketMQListener<Order> {
    @Override
    public void onMessage(Order order) {
        log.info("收到一个订单消息：{}", JSON.toJSONString(order));
    }
}
