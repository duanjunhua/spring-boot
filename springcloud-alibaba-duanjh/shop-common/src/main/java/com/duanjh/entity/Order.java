package com.duanjh.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-08-24
 * @Version: V1.0
 * @Description: 订单
 */
@Data
@Entity(name = "shop_order")
public class Order implements Serializable {

    public static final long serialVersionUID = 3L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;


    /**
     * 用户ID
     */
    private Integer uid;

    /**
     * 用户名
     */
    private String username;


    /**
     * 商品ID
     */
    private Integer pid;

    /**
     * 商品名称
     */
    private String pname;

    /**
     * 商品单价
     */
    private Double pprice;

    /**
     * 购买数量
     */
    private Integer number;

}
