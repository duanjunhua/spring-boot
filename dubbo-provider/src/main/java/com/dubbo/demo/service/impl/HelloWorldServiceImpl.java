package com.dubbo.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.demo.service.HelloWorldService;
/**
 *	注册为Dubbo服务，导入的为Dubbo Service注解 
 */
@Service(version="1.0")
public class HelloWorldServiceImpl implements HelloWorldService {

	public String hello(String name) {
		return "Hello World! " + name;
	}
}
