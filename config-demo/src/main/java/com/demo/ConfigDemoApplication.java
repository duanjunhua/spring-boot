/**
 * @Author: Michael
 * @Date: 2018年12月3日_下午5:22:18
 * @Version: v0.1
 */
package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableSwagger2
public class ConfigDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigDemoApplication.class, args);
	}
}
