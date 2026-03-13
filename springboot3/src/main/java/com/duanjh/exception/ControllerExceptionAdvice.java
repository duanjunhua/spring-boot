package com.duanjh.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-12 周四 17:26
 * @Version: v1.0
 * @Description: 统一异常处理
 *  ResponseEntityExceptionHandler默认实现了常用的异常处理，它输出的格式 是spring默认的
 */
@ControllerAdvice(basePackages = "com.duanjh")
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> deatil = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> {
            deatil.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(new ErrorRes(status.value(), ex.getBody().getDetail(), deatil), status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new ErrorRes(status.value(), ex.getErrorCode(), ex.getLocalizedMessage()), status);
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler({PersonalException.class})
    protected ResponseEntity<Object> handlePersonalException(PersonalException ex){
        // 自定义错误码，此处Demo使用BAD_REQUEST
        return new ResponseEntity<>(new ErrorRes(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
