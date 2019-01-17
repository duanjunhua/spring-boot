package com.djh.demo.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.djh.demo.Response;
import com.djh.demo.beans.User;
import com.djh.demo.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private MessageSource messageSource;

	public Response findAllUser() {
		Response response = new Response();
		List<User> users = userMapper.findAllUser();
		if (CollectionUtils.isEmpty(users)) {
			String message = messageSource.getMessage("no_user_exist", null, Locale.getDefault());
			response.setData(message);
			response.setStatus(Response.STATUS_WARNING);
			return response;
		}
		response.setData(users.toString());
		response.setStatus(Response.STATUS_SUCCESS);
		return response;
	}

	public Response findUserById(int id) {
		Response response = new Response();
		User user = userMapper.getUserById(id);
		if (user == null) {
			String message = messageSource.getMessage("no_user_exist", new Object[] { id }, Locale.getDefault());
			response.setMessage(message);
			response.setStatus(Response.STATUS_WARNING);
			return response;
		}
		response.setStatus(Response.STATUS_SUCCESS);
		response.setData(user.toString());
		return response;
	}

	public Response saveUser(User user) {
		Response response = new Response();
		if (userMapper.emailAlreadyExist(user.getEmail())) {
			String message = messageSource.getMessage("email_already_registered", new Object[] { user.getEmail() }, Locale.getDefault());
			response.setMessage(message);
			response.setStatus(Response.STATUS_REJECT);
			return response;
		}
		if (userMapper.userAlreadyExist(user.getUserName())) {
			String message = messageSource.getMessage("updated_user_info", new Object[] { user.getEmail() }, Locale.getDefault());
			userMapper.updateUserInfo(user);
			response.setMessage(message);
			response.setStatus(Response.STATUS_WARNING);
			return response;
		}
		userMapper.saveUser(user);
		response.setStatus(Response.STATUS_SUCCESS);
		return response;
	}

}
