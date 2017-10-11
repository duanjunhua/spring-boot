package com.djh.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.djh.demo.Response;
import com.djh.demo.beans.User;
import com.djh.demo.service.UserService;

@RestController("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/findUser")
	public Response findUserList() {
		return userService.findAllUser();
	}

	@GetMapping("/findUserById/{id}")
	public Response findUserById(@PathVariable("id") int id) {
		return userService.findUserById(id);
	}

	@PostMapping("/add")
	public Response saveNewUser(@RequestBody(required = true) User user) {
		return userService.saveUser(user);
	}
}
