package com.michael.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableHystrix				//Enable Hystrix
@EnableEurekaClient			//enable current app as an client
@EnableFeignClients			//enable feign
@SpringBootApplication
public class HystrixClientApplication {

	/**
	 * hystrix acturator address: http://localhost:8082/hystrix.stream
	 */
	public static void main(String[] args) {
		SpringApplication.run(HystrixClientApplication.class, args);
	}

}
