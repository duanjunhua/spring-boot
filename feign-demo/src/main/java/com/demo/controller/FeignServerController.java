/**
 * @Author: Michael
 * @Date: 2018��12��3��_����5:30:09
 * @Version: v0.1
 */
package com.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

/**
 * @author Administrator
 *
 */
@Log4j2
@RestController
@RequestMapping("/feign-server-controller")
public class FeignServerController {

	@Value("${username}")
	private String username;
	
	@Value("${password}")
	private String password;
	
	@GetMapping("/")
	public void index() {
		log.info("index Page");
	}
	
	@GetMapping("/access_config_server")
	public String accessConfigServer() {
		log.debug(username);
		return username + " : " + password;
	}
	
	@GetMapping("/feign_api")
	public String feignApi(String client) {
		log.info("Feign Api Calling Client...");
		return "Feign Api data, " + client;
	}
}
