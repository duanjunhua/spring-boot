package com.boot.vue.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-08
 * @Version: V1.0
 * @Description:
 */
@Data
public class SupplierREQ implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系电话
     */
    private String mobile;

}
