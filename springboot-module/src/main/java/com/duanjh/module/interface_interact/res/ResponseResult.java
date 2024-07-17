package com.duanjh.module.interface_interact.res;

import com.duanjh.module.interface_interact.enu.ResultCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Author: Michale J H Duan[JunHua]
 * @Date: 2024-07-16 星期二 15:52
 * @Version: v1.0
 * @Description: 统一响应结果
 */
@Data
public class ResponseResult implements Serializable {

    private Integer code;

    private String msg;

    private Object data;

    public ResponseResult(){

    }

    public ResponseResult(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
        this.data = data;
    }

    public static ResponseResult success(){
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    public static ResponseResult success(Object data){
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    public static ResponseResult fail(){
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMsg(ResultCode.FAIL.getMessage());
        return result;
    }

    public static ResponseResult fail(String msg){
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMsg(msg);
        return result;
    }

    public static ResponseResult fail(String msg, Object data){
        ResponseResult result = fail();
        if(StringUtils.isNotBlank(msg)) {
            result.setMsg(msg);
        }
        result.setData(data);
        return result;
    }
}
