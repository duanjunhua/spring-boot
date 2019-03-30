package com.michael.hystrix.feign;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

/**
 * Use factory can be found the detail error message
 */
@Component
public class HystrixFeignFallbackFactory implements FallbackFactory<HystrixFeign> {

	@Override
	public HystrixFeign create(Throwable cause) {
		return new HystrixFeign() {
			
			@Override
			public String normal() {
				System.out.println(cause.getMessage());
				return null;
			}
			
			@Override
			public String hystrix() {
				System.out.println(cause.getMessage());
				return null;
			}
		};
	}

}
