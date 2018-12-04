/**
 * @Author: Michael
 * @Date: 2018年12月3日_下午5:30:09
 * @Version: v0.1
 */
package com.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 *
 */
@RestController
public class RestServerController {

	@Value("${username}")
	private String username;
	
	@Value("${password}")
	private String password;
	
	@GetMapping("/")
	public void index() {
		System.out.println("index Page");
	}
	
	@GetMapping("/accessConfigServer")
	public String accessConfigServer() {
		return username + " : " + password;
	}
	
}
