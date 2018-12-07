/**
 * @Author: Michael
 * @Date: 2018��12��3��_����5:22:18
 * @Version: v0.1
 */
package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableFeignClients
@SpringBootApplication
public class FeignDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(FeignDemoApplication.class, args);
	}
}
