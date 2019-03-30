package com.michael.hystrix.feign;

import org.springframework.stereotype.Component;

@Component
public class HystrixFeignFallback implements HystrixFeign {

	@Override
	public String normal() {
		System.out.println("hystrix method fallback");
		return "normal fallback";
	}

	@Override
	public String hystrix() {
		System.out.println("hystrix method fallback");
		return "hystrix fallback";
	}

}
