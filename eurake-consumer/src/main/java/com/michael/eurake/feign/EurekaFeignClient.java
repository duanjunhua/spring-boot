package com.michael.eurake.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client")
public interface EurekaFeignClient {

	@GetMapping("/client/message")
	public String consume();
}
