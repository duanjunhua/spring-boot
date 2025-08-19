package com.duanjh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-18 周一 16:56
 * @Version: v1.0
 * @Description:
 */
@Data
@TableName("t_data_source")
public class DataSourceEntity {


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 数据库地址
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String userName;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 数据库驱动
     */
    private String driverClassName;

    /**
     * 数据库名
     */
    private String datasourceName;
}
