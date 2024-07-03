package com.duanjh.module.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-07-03 周三
 * @Version: v1.0
 * @Description: 全局异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public String runtimeExceptionHandler(Exception e) {
        log.error("RuntimeException with exception message: {}", e.getLocalizedMessage());
        return e.getLocalizedMessage();
    }
}
