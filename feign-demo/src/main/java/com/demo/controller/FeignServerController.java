/**
 * @Author: Michael
 * @Date: 2018年12月3日_下午5:30:09
 * @Version: v0.1
 */
package com.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Response;

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
	public Response accessConfigServer() {
		log.debug(username);
		Response response = new Response();
		response.setStatus(Response.STATUS_REJECT);
		response.setMessage(username + " : " + password);
		return response;
	}
	
	@GetMapping("/feign_api")
	public Response feignApi(String client) {
		log.info("Feign Api Calling Client...");
		Response response = new Response();
		response.setStatus(Response.STATUS_SUCCESS);
		response.setData("Feign Api data, " + client);
		return response;
	}
	
	@PostMapping("/submit")
	public Response postData() {
		Response response = new Response();
		response.setStatus(Response.STATUS_ERROR);
		response.setMessage("Post Data with error message!");
		return response;
	}
}
