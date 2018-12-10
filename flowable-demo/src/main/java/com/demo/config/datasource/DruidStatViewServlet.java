package com.demo.config.datasource;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * @author Michael
 * @Date: 2018年12月7日_下午3:52:11
 * @Version: v0.1
 */
@WebServlet(
	urlPatterns = {"/druid/*"},
	initParams = {
			@WebInitParam(name="loginUsername", value="admin"),
			@WebInitParam(name="loginPassword", value="test"),
			@WebInitParam(name="resetEnable", value="false")
	})
public class DruidStatViewServlet extends StatViewServlet {

	 private static final long serialVersionUID = 1L;
	 
}
