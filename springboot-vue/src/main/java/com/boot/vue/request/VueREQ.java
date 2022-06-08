package com.boot.vue.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-07
 * @Version: V1.0
 * @Description: 查询条件请求类
 */
@Data
public class VueREQ implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 卡号
     */
    private String cardNum;

    /**
     * 支付类型（1-现金，2-微信，3-支付宝，4-银行卡）
     */
    private String payType;

    /**
     * 生日
     */
    private Date birthday;
}
