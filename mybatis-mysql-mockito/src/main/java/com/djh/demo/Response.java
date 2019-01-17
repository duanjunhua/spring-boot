package com.djh.demo;

public class Response {

	public static final String STATUS_SUCCESS = "SUCCESS";
	public static final String STATUS_WARNING = "WARNING";
	public static final String STATUS_ERROR = "ERROR";
	public static final String STATUS_FAIL = "FAIL";
	public static final String STATUS_REJECT = "REJECT";

	private String message;
	private String status;
	private String data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
