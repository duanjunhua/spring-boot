package com.michael.eurake.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class EurekaClientController {

	@GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getMessage(HttpServletRequest request) {
		return request.getRequestURL().toString();
	}
	
}
