package com.boot.vue.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-06
 * @Version: V1.0
 * @Description: 规范统一响应枚举
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    //成功
    SUCCESS(2000, "成功"),

    //错误
    ERROR(999, "错误");

    private Integer code;

    private String desc;

}
