package com.duanjh.controller;

import com.alibaba.fastjson.JSON;
import com.duanjh.entity.Order;
import com.duanjh.entity.Product;
import com.duanjh.rest.ProductService;
import com.duanjh.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-10-09
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/order-mq")
public class OrderMqController {

    private OrderService orderService;

    private ProductService productService;

    private RocketMQTemplate rocketmqTemplate;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setRocketmqTemplate(RocketMQTemplate rocketmqTemplate) {
        this.rocketmqTemplate = rocketmqTemplate;
    }

    @GetMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid){
        log.info(">>客户下单，这时候要调用商品服务查询商品信息");
        Order order = new Order();

        Product product = productService.findById(pid);
        log.info(">>商品信息，查询结果：{}", JSON.toJSONString(product));

        //集成Sentinel
        if(product.getPid() == -1){
            Order order1 = new Order();
            order1.setPname("下单失败");
            return order1;
        }

        order.setUid(2);
        order.setUsername("Test01");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderService.save(order);


        //1. 同步发送：同步发送是指消息发送方发出数据后，会在收到接收方发回响应之后才发下一个数据包的通讯方式，此种方式应用场景非常广泛，例如重要通知邮件、报名短信通知、营销短信系统等
        //发送TPS快、发送结果有反馈、数据可靠不丢失
        SendResult sendResult = rocketmqTemplate.syncSend("sync-order-topic", "同步消息");
        log.info("同步消息发送结果：{}", JSON.toJSONString(sendResult));

        //2. 异步消息：异步发送是指发送方发出数据后，不等接收方发回响应，接着发送下个数据包的通讯方式。发送方通过回调接口接收服务器响应，并对响应结果进行处理
        //            一般用于链路耗时较长，对 RT 响应时间较为敏感的业务场景，例如用户视频上传后通知启动转码服务，转码完成后通知推送转码结果等
        //发送TPS快、发送结果有反馈、数据可靠不丢失
        rocketmqTemplate.asyncSend("async-order-topic", "异步消息", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步消息发送结果：{}", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("异步发送异常信息：{}", JSON.toJSONString(throwable));
            }
        });

        log.info("异步执行完毕...");

        //3. 单向消息：单向发送是指发送方只负责发送消息，不等待服务器回应且没有回调函数触发，即只发送请求不等待应答，适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集
        //发送TPS最快、无发送结果反馈、数据可能丢失
        rocketmqTemplate.sendOneWay("oneway-order-topic", "单向消息");


        /**
         * 顺序消息
         */
        rocketmqTemplate.syncSendOrderly("sync-order-topic-orderly", "异步顺序消息", "1");

        /**
         * 事务消息：通过事务消息就能达到分布式事务的最终一致
         */


        rocketmqTemplate.convertAndSend("order-topic", order);

        return order;
    }

}
