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
 * @Description: 商品
 */
@Data
@Entity(name = "shop_product")
public class Product implements Serializable {

    public static final long serialVersionUID = 2L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    /**
     * 商品名称
     */
    private String pname;

    /**
     * 商品价格
     */
    private Double pprice;

    /**
     * 库存
     */
    private Integer stock;

}
