package com.michael.eurake.controller;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@Configuration		//config to set restTemplate load by spring
@RestController
public class EurekaConsumerController {

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@GetMapping("/consume")
	public String consume() {
		String message = restTemplate().getForObject("http://eureka-client/client/message", String.class);
		return message;
	}
}
