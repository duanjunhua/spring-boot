package com.demo.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

/**
 * @author Michael
 * @Date: 2018年12月7日_上午10:01:12
 * @Version: v0.1
 */
@Log4j2
public class AuthorizationInterceptor implements HandlerInterceptor {

	private String token;
	
	public AuthorizationInterceptor(String token) {
		this.token = token;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(accessToken == null || !StringUtils.equals(accessToken, token)) {
        	log.info("Authorization token is null or not correct");
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        	return false;
        }
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
