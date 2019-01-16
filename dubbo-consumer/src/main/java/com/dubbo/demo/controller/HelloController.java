package com.dubbo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dubbo.demo.service.HelloConsumerService;

@RestController
@RequestMapping("hello-controller")
public class HelloController {

	@Autowired
	HelloConsumerService helloConsumerService;
	
	@RequestMapping("/hello")
	public String hello(String name) {
		String msg = helloConsumerService.rest(name);
		System.out.println(msg);
		return msg;
	}
}
