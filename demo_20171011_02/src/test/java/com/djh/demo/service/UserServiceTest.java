package com.djh.demo.service;

import static org.mockito.Matchers.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;

import com.djh.demo.Response;
import com.djh.demo.beans.User;
import com.djh.demo.mapper.UserMapper;

import junit.framework.TestCase;

/**
 * @author Administrator
 * @Date Oct 10, 2017
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends TestCase {

	@Mock
	private UserMapper userMapper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private User user;
	@Mock
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(userService, "userMapper", userMapper, UserMapper.class);
		ReflectionTestUtils.setField(userService, "messageSource", messageSource, MessageSource.class);
	}

	@Test
	// @Ignore
	// @Order(1)
	public void testFindAllUserIfUserIsEmpty() {
		Response response = new Response();

		when(userMapper.findAllUser()).thenReturn(Collections.<User>emptyList());

		doCallRealMethod().when(userService).findAllUser();

		response = this.userService.findAllUser();

		assertEquals("The status not equals WARNING when no user exist.", Response.STATUS_WARNING, response.getStatus());

		verify(userMapper, times(1)).findAllUser();
		// verify(this.messageSource, times(1)).getMessage(any(), any(), any());
	}

	@Test
	// @Ignore
	public void testFindAllUserIfUserIsNotEmpty() {
		Response response = new Response();

		when(userMapper.findAllUser()).thenReturn(Collections.singletonList(this.user));

		doCallRealMethod().when(userService).findAllUser();

		response = this.userService.findAllUser();

		assertEquals("The status not equals SUCCESS when user exist.", Response.STATUS_SUCCESS, response.getStatus());

		verify(userMapper, times(1)).findAllUser();
		// verify(this.messageSource, times(0)).getMessage(any(), any(), any());
	}

	@Test
	// @Ignore
	public void testFindUserByIdIfIdNotExist() {
		Response response = new Response();
		this.user = null;
		when(userMapper.getUserById(anyInt())).thenReturn(user);

		doCallRealMethod().when(this.userService).findUserById(anyInt());

		response = this.userService.findUserById(anyInt());

		assertEquals("The status is not WARNING when id not exist.", Response.STATUS_WARNING, response.getStatus());

		verify(this.userMapper, times(1)).getUserById(anyInt());
		// verify(this.messageSource, times(1)).getMessage(any(), any(), any());
	}

	@Test
	// @Ignore
	public void testFindUserByIdIfIdExist() {
		Response response = new Response();

		when(userMapper.getUserById(anyInt())).thenReturn(this.user);

		doCallRealMethod().when(this.userService).findUserById(anyInt());

		response = this.userService.findUserById(anyInt());

		assertEquals("The status is not SUCCESS when id exist.", Response.STATUS_SUCCESS, response.getStatus());

		verify(this.userMapper, times(1)).getUserById(anyInt());
		// verify(this.messageSource, times(0)).getMessage(any(), any(), any());
	}

	@Test
	// @Ignore
	public void testSaveUserIfEmailAlreadyExist() {
		Response response = new Response();

		emailExistValid(true, false);

		response = this.userService.saveUser(this.user);

		assertEquals("The status is not REJECT when email already exist.", Response.STATUS_REJECT, response.getStatus());

		verify(this.userMapper, times(1)).emailAlreadyExist(anyString());
		// verify(this.messageSource, times(1)).getMessage(any(), any(), any());
		verify(this.userMapper, times(0)).userAlreadyExist(anyString());
		verify(this.userMapper, times(0)).updateUserInfo(any(User.class));
		verify(this.userMapper, times(0)).saveUser(any(User.class));
	}

	@Test
	// @Ignore
	public void testSaveUserIfEmailNotExistAndUserExist() {
		Response response = new Response();

		emailExistValid(false, true);

		response = this.userService.saveUser(this.user);

		assertEquals("The status is not WARNING when email not exist but user exist.", Response.STATUS_WARNING, response.getStatus());

		verify(this.userMapper, times(1)).emailAlreadyExist(anyString());
		// verify(this.messageSource, times(1)).getMessage(any(), any(), any());
		verify(this.userMapper, times(1)).userAlreadyExist(anyString());
		verify(this.userMapper, times(1)).updateUserInfo(any(User.class));
		verify(this.userMapper, times(0)).saveUser(any(User.class));
	}

	@Test
	// @Ignore
	public void testSaveUserIfEmailNotExistAndUserNotExist() {
		Response response = new Response();

		emailExistValid(false, false);

		response = this.userService.saveUser(this.user);

		assertEquals("The status is not SUCCESS when email and user both not exist.", Response.STATUS_SUCCESS, response.getStatus());

		verify(this.userMapper, times(1)).emailAlreadyExist(anyString());
		// verify(this.messageSource, times(0)).getMessage(any(), any(), any());
		verify(this.userMapper, times(1)).userAlreadyExist(anyString());
		verify(this.userMapper, times(0)).updateUserInfo(any(User.class));
		verify(this.userMapper, times(1)).saveUser(any(User.class));
	}
	
	private void emailExistValid(Boolean emailExist, Boolean userExist) {
		this.user.setEmail("testEmail");
		this.user.setUserName("testName");
		when(userMapper.emailAlreadyExist(anyString())).thenReturn(emailExist);
		when(userMapper.userAlreadyExist(anyString())).thenReturn(userExist);
		doCallRealMethod().when(userService).saveUser(this.user);
	}
}
