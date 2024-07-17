package com.duanjh.module.interface_interact.enu;

/**
 * @Author: Michale J H Duan[JunHua]
 * @Date: 2024-07-16 星期二 15:39
 * @Version: v1.0
 * @Description: 状态枚举
 */
public enum ResultCode {
    // 创建对象，必须在第一行
    /**
     * 接口响应成功
     */
    SUCCESS(200, "成功"),

    /**
     * 接口相应失败
     */
    ERROR(500, "服务器异常！"),
    FAIL(501, "服务请求失败！"),

    /**
     * 参数错误
     */
    PARAMS_IS_INVALID(1001, "参数无效"),
    PARAMS_IS_BLANK(1002, "参数为空"),
    PARAMS_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAMS_NOT_COMPLETE(1004, "参数缺失"),

    /**
     * 用户错误
     */
    USER_NOT_LOGGER_IN(2001, "用户未登录，请登录！"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIT(2003, "账号被禁用"),
    USER_NOT_EXISTS(2004, "用户不存在"),
    USER_HAS_EXISTED(2006, "用已存在");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
