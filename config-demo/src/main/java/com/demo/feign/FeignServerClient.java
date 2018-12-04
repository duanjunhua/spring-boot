package com.demo.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Michael
 * @Date: 2018��12��4��_����3:07:54
 * @Version: v0.1
 */
@FeignClient(name="feign-demo", url="http://localhost:8055")		//name���Կ�������Զ��壬ָ��URL���Բ�����Eureka����
public interface FeignServerClient {

	@GetMapping("/feign-server-controller/feign_api")
	String feignService(@RequestParam("client") String client);
}
