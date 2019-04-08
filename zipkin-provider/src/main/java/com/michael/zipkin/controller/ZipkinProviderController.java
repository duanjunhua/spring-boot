package com.michael.zipkin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zipkin-provider")
public class ZipkinProviderController {

	@RequestMapping("/hello_world")
	private String helloWorld(String name) {
		return "Hello World! " + name;
	}
	
}
