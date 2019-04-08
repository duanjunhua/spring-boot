package com.michael.zipkin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="zipkin-provider", url="http://localhost:8082")
public interface ZipkinProviderFeign {
	
	@RequestMapping("/zipkin-provider/hello_world")
	String helloWorld(@RequestParam("name") String name);
}
