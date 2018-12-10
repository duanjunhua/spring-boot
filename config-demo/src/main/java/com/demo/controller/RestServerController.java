/**
 * @Author: Michael
 * @Date: 2018��12��3��_����5:30:09
 * @Version: v0.1
 */
package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.feign.FeignServerClient;

@RestController
@RequestMapping("/rest-server-controller")
public class RestServerController {

	@Value("${username}")
	private String username;
	
	@Value("${password}")
	private String password;
	
	@Autowired
	private FeignServerClient feignServerClient;
	
	@GetMapping("/")
	public void index() {
		System.out.println("index Page");
	}
	
	@GetMapping("/accessConfigServer")
	public String accessConfigServer() {
		System.out.println(username);
		return username + " : " + password;
	}
	
	@GetMapping("/accessFeign")
	public String accessFeign() {
		return feignServerClient.feignService("config-demo");
	}
}
