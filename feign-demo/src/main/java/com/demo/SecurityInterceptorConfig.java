package com.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.demo.security.AuthorizationInterceptor;

/**
 * @author Michael
 * @Date: 2018年12月7日_上午11:36:59
 * @Version: v0.1
 */
@Configuration
public class SecurityInterceptorConfig extends WebMvcConfigurerAdapter {

	@Value("${security.token}")
	private String token;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new AuthorizationInterceptor(token)).addPathPatterns("/feign-server-controller/**");
	}
}
