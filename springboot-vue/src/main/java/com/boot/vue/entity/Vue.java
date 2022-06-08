package com.boot.vue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-07
 * @Version: V1.0
 * @Description: 信息表对应实体类
 */
@Data
@Accessors(chain = true)
@TableName("tb_vue")
public class Vue implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 卡号
     */
    private String cardNum;

    /**
     * 名称
     */
    private String name;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 可用积分
     */
    private Integer integral;

    /**
     * 可用金额
     */
    private Double money;

    /**
     * 支付类型（1-现金，2-微信，3-支付宝，4-银行卡）
     */
    private String payType;

    /**
     * 地址
     */
    private String address;

    /**
     * 供应商名称，非表字段，使用exist=false进行标注
     */
    @TableField(exist = false)
    private String supplierName;
}
