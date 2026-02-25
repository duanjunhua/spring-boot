package com.duanjh.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-09 周一 17:06
 * @Version: v1.0
 * @Description: Fanout Exchange:广播模式或者订阅模式配置
 */
@Configuration
public class FanoutRabbitmqConfig {

    public static final String FANOUT_EXCHANGE = "fanoutExchange";

    /**
     * 使用primary、secondary两个队列绑定到 Fanout 交换机上面
     */

    @Bean
    public Queue primaryFanout(){
        // 表示创建默认的队列
        return new Queue("fanout.primary");
    }

    @Bean
    public Queue secondaryFanout(){
        // 表示创建默认的队列
        return new Queue("fanout.secondary");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeA(Queue primaryFanout, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(primaryFanout).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue secondaryFanout, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(secondaryFanout).to(fanoutExchange);
    }

}
