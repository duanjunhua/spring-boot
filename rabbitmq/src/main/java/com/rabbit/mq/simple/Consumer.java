package com.rabbit.mq.simple;

import java.io.IOException;

import com.rabbit.mq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Consumer {

	private static final String QUEUE = "rabbit_simple";
	
	public static void main(String[] args) throws Exception{
		
		//1.获取连接
		Connection connection = RabbitMQUtils.getConnection();
		
		//2.创建通道
		Channel channel = connection.createChannel();
		
		//3.声明MQ
		channel.queueDeclare(QUEUE, false, false, false, null);
		
		//4.创建消费者
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("接收者接收消息：" + new String(body));
				//确认响应
				channel.basicAck(envelope.getDeliveryTag(), false);		//false表示确认响应
			}
		};
		
		//5.消费者监听接收消息
		while (true) {
			channel.basicConsume(QUEUE, false, consumer);
		}
		
	}
}
