package com.example.hystrix.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hystrix-provider")
public class HystrixProviderController {

	@GetMapping(value = "/normal_request", produces = MediaType.APPLICATION_JSON_VALUE)
	public String normalRequest(HttpServletRequest request) {
		return request.getRequestURL().toString();
	}
	
	@GetMapping(value = "/hystrix_request", produces = MediaType.APPLICATION_JSON_VALUE)
	public String hystrixRequest(HttpServletRequest request) throws Exception {
		System.out.println(request.getRequestURL().toString());
		Thread.sleep(8000);
		return "hystrix";
	}
}
