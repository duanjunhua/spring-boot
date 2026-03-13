package com.duanjh.exception;

import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-13 周五 17:15
 * @Version: v1.0
 * @Description: 自定义异常类
 */
@Component
public class PersonalException extends RuntimeException {


    public PersonalException() {
        super();
    }

    public PersonalException(String message) {
        super(message);
    }
}
