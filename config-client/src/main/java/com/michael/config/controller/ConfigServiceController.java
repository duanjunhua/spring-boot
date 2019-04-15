package com.michael.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope	//define this controller(username and password will update) will refresh when call /actuator/refresh point
@RestController
public class ConfigServiceController {

	@Value("${datasource.user.name}")
	private String username;
	@Value("${datasource.user.password}")
	private String password;
	
	@GetMapping("/props")
	public String props() {
		return username + " : " + password;
	}
}
