package com.rabbit.mq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {

	public static Connection getConnection() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		factory.setUsername("rabbitmq");
		factory.setPassword("rabbitmq");
		factory.setVirtualHost("/rabbitmq");
		return factory.newConnection();
	}
}
