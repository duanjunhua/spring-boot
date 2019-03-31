package com.michael.zuul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("zuul-provider")
public class ZuulProviderController {

	@GetMapping("/hello")
	public String hello() {
		return "hello, zuul";
	}
}
