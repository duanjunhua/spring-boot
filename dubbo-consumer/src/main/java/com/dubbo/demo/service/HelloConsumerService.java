package com.dubbo.demo.service;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

@Component
public class HelloConsumerService {

	@Reference(version="1.0")
	HelloWorldService helloWorldService;		//不能再Controller中直接Reference,需要使用Component优先加载Bean
	
	public String rest(String name) {
		return helloWorldService.hello(name);
	}
}
