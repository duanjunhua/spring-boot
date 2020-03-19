package com.fxkj.response;

/**
 * @author: Michael J H Duan
 * @version: v1.0
 * @date Date: 2020-03-18 16:58
 */
public class Response {

    public static final String RES_SUC = "SUCCESS";
    public static final String RES_WRN = "WARNING";
    public static final String RES_ERR = "ERROR";
    public static final String RES_FAL = "FAIL";

    private String code;
    private String msg;
    private Object data;

    public Response(String code) {
        super();
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
