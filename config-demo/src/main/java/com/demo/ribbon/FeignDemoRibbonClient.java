package com.demo.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;

/**
 * @author Michael
 * @Date: 2018年12月4日_下午4:28:33
 * @Version: v0.1
 */
@Configuration
@RibbonClient(name="feign-demo", configuration=FeignDemoRibbonConfiguration.class)
public class FeignDemoRibbonClient {

	@Bean
	public IPing ribbonPing(IClientConfig config) {
		return null;
	}
}
