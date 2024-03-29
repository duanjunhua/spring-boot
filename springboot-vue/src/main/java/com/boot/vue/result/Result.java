package com.boot.vue.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-06
 * @Version: V1.0
 * @Description: 规范响应统一结果
 */
@Slf4j
@Data
public class Result implements Serializable {

   private static final long serialVersionUID = 1L;

    /**
     * 响应业务状态码
     */
   private Integer code;

    /**
     * 是否正常
     */
    private Boolean flag;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应中的数据
     */
    private Object data;

    public Result(Integer code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
        this.flag = code == ResultEnum.SUCCESS.getCode();
    }

    public static Result ok(){
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), null);
    }

    public static Result ok(Object data){
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), data);
    }

    public static Result ok(String message, Object data){
        return new Result(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static Result error(String message){
        log.warn("返回错误： code={}，message={}", ResultEnum.ERROR.getCode(), message);
        return new Result(ResultEnum.ERROR.getCode(), message, null);
    }

    public static Result build(int code, String message){
        log.warn("返回结果： code={}，message={}", code, message);
        return new Result(code, message, null);
    }

    public static Result build(ResultEnum resultEnum){
        log.warn("返回结果： code={}，message={}", resultEnum.getCode(), resultEnum.getDesc());
        return new Result(resultEnum.getCode(), resultEnum.getDesc(), null);
    }

    public String toString(){
        return JSON.toJSONString(this);
    }

}
