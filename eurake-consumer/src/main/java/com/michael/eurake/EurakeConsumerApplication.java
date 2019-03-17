package com.michael.eurake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class EurakeConsumerApplication {

	/**
	 * Feign的默认配置：
	 * 解码器(Decoder)：Bean的名称为feignDecoder, ResponseEntityDecoder类
	 * 编码器(Encoder)：Bean名称为feignEncoder, SpringEncoder类
	 * 日志(Logger)：Bean名称为feignLogger, slf4jLogger类
	 * 注解翻译器(Contract)：Bean名称为feignContract,SpringMvcContract类
	 * Feign实例的创建者(Feign.Builder)：Bean名称为feignBuilder, HystrixFeign.Builder类
	 * Feign客户端(Client)：Bean名称为feignClient, LoadBalancerFeignClient类
	 * 
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(EurakeConsumerApplication.class, args);
	}

}
