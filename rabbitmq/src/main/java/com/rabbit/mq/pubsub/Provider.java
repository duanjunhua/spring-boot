package com.rabbit.mq.pubsub;

import com.rabbit.mq.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Provider {

	private static final String EXCHANGE = "rabbit_ex_pubsub";
	
	public static void main(String[] args) throws Exception{
		
		//1.获取连接
		Connection connection = RabbitMQUtils.getConnection();
		
		//2.创建通道
		Channel channel = connection.createChannel();
		
		//3.声明交换机
		//参数1：交换机名称
		//参数2：声明交换机类型，主要有以下类型，主要使用前三种类型：
		/**
		 * 1. fanout： 会把所有发送到该Exchange的消息路由到所有与它绑定的Queue中
		 * 2. direct： 会把消息路由到那些binding key与routing key完全匹配的Queue中
		 * 3. topic： 模糊匹配，可以通过通配符满足一部分规则就可以传送，其中"*"用于匹配一个单词，"#"用于匹配多个单词（可以是零个）
		 * 4. headers： headers类型的Exchange不依赖于routing key与binding key的匹配规则来路由消息，而是根据发送的消息内容中的headers属性进行匹配
		 */
		channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT);	//fanout：发布订阅模式
		
		//4.发送消息
		channel.basicPublish(EXCHANGE, "",null, ("rabbitMQ [fanout] type Pub & Sub Queue通信测试 ").getBytes());
		channel.close();
		connection.close();
		System.out.println("发送完毕......");
	}
}
