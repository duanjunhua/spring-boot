package com.michael.zipkin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michael.zipkin.feign.ZipkinProviderFeign;

@RestController
@RequestMapping("/zipkin-consumer")
public class ZipkinConsumerController {

	@Autowired
	private ZipkinProviderFeign zipkinProviderFeign;
	
	@RequestMapping("/hello")
	private void hello(String name) {
		System.out.println(zipkinProviderFeign.helloWorld(name));
	}
}
