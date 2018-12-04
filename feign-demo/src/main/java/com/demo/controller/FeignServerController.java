/**
 * @Author: Michael
 * @Date: 2018年12月3日_下午5:30:09
 * @Version: v0.1
 */
package com.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/feign-server-controller")
public class FeignServerController {

	@Value("${username}")
	private String username;
	
	@Value("${password}")
	private String password;
	
	@GetMapping("/")
	public void index() {
		System.out.println("index Page");
	}
	
	@GetMapping("/access_config_server")
	public String accessConfigServer() {
		System.out.println(username);
		return username + " : " + password;
	}
	
	@GetMapping("/feign_api")
	public String feignApi(String client) {
		return "Feign Api data, " + client;
	}
}
