package com.duanjh.oauth.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 16:13
 * @Version: v1.0
 * @Description: 统一返回
 */
@Data
@AllArgsConstructor
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    public static <T> ResponseEntity<Result<T>> success(T data) {
        return ResponseEntity.ok(new Result<>(200, "操作成功", data));
    }

    public static <T> ResponseEntity<Result<T>> success(String msg) {
        return ResponseEntity.ok(new Result<>(200, msg, null));
    }

    public static <T> ResponseEntity<Result<T>> fail(String msg) {
        return ResponseEntity.ok(new Result<>(500, msg, null));
    }

}
