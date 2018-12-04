package com.demo.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Michael
 * @Date: 2018年12月4日_下午3:07:54
 * @Version: v0.1
 */
@FeignClient(name="feign-demo", url="http://localhost:8055")		//name属性可以随便自定义，指定URL可以不适用Eureka服务
public interface FeignServerClient {

	@GetMapping("/feign-server-controller/feign_api")
	String feignService(@RequestParam("client") String client);
}
