package com.duanjh;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.rabbitmq.FanoutRabbitmqConfig;
import com.duanjh.rabbitmq.MqReceiver;
import com.duanjh.rabbitmq.MqSender;
import com.duanjh.rabbitmq.RabbitmqConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-09 周一 15:24
 * @Version: v1.0
 * @Description: MQ消息队列测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTests {

    @Autowired
    MqSender sender;

    @Autowired
    MqReceiver receiver;

    @Test
    public void defaultTopicTest(){
        sender.defaultTopicSend("hello");
    }

    @Test
    public void dynamicTopicStringMessageTest(){
        sender.dynamicTopicSend("", RabbitmqConfig.DEFAULT_QUEUE, "Duanjh");
        Object result = receiver.processWithTopic(RabbitmqConfig.DEFAULT_QUEUE);

        Assert.assertEquals("Duanjh", result);
    }

    @Test
    public void dynamicTopicObjectDataTest(){
        sender.dynamicTopicSend(MqSender.DEFAULT_EXCHANGE, "entity", new BootUser("duanjh", "Duanjh@123", "duanjh@qq.com"));
        BootUser result = (BootUser) receiver.processWithTopic("entity");
        Assert.assertEquals("duanjh", result.getUsername());
    }

    @Test
    public void fanoutBroadCastTest(){
        sender.dynamicTopicSend(FanoutRabbitmqConfig.FANOUT_EXCHANGE, "", "广播消息");
        Object primaryRes = receiver.processWithTopic("fanout.primary");
        Assert.assertNotNull(primaryRes);
        Assert.assertEquals("广播消息", primaryRes);

        Object secondaryRes = receiver.processWithTopic("fanout.secondary");
        Assert.assertNotNull(secondaryRes);
        Assert.assertEquals("广播消息", secondaryRes);
    }

}
