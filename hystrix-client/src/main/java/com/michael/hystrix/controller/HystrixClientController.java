package com.michael.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michael.hystrix.feign.HystrixFeign;

@RestController
@RequestMapping("hystrix-client")
public class HystrixClientController {

	@Autowired
	HystrixFeign hystrixFeign;
	
	@GetMapping("/normal")
	public String noraml() {
		return hystrixFeign.normal();
	}
	
	@GetMapping("/hystrix")
	public String hystrix() {
		return hystrixFeign.hystrix();
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}
}
