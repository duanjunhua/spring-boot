package com.michael.hystrix.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "hystrix-server", fallback = HystrixFeignFallback.class /*, fallbackFactory = HystrixFeignFallbackFactory.class*/)
public interface HystrixFeign {

	@GetMapping("/hystrix-provider/normal_request")
	public String normal();
	
	@GetMapping("/hystrix-provider/hystrix_request")
	public String hystrix();
}
