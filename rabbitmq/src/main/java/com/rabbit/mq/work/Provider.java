package com.rabbit.mq.work;

import com.rabbit.mq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Provider {

	private static final String QUEUE = "rabbit_work";
	
	public static void main(String[] args) throws Exception{
		
		//1.获取连接
		Connection connection = RabbitMQUtils.getConnection();
		
		//2.创建通道
		Channel channel = connection.createChannel();
		
		//3.声明MQ，参数1：队列名
		//参数2：数据是否持久化，若为false，服务停止或断电会丢失数据
		//参数3：是否是独占队列，若为true，怎只能绑定到当前队列的接收者能接接收数据
		//参数4：是否自动删除队列，若为true，接收者响应后会删除数据
		//参数5：其他参数
		channel.queueDeclare(QUEUE, false, false, false, null);
		
		System.out.println("start---------------");
		//4.发送消息
		for (int i = 0; i < 50; i++) {
			System.out.println(i);
			channel.basicPublish("", QUEUE,null, ("rabbitMQ work Queue通信测试 " + i).getBytes());
		}
		channel.close();
		connection.close();
	}
}
