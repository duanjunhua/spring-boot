package com.duanjh.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-13 周五 17:09
 * @Version: v1.0
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 错误信息具体描述
     */
    private Object detail;

}
