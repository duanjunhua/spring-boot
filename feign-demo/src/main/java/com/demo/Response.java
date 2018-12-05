package com.demo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Michael
 * @Date: 2018��12��5��_����4:58:44
 * @Version: v0.1
 */
@Getter
@Setter
public class Response {

	public static final String STATUS_SUCCESS = "success";
	public static final String STATUS_REJECT = "reject";
	public static final String STATUS_ERROR = "error";
	
	private String status;
	private String data;
	private String message;
	
}
