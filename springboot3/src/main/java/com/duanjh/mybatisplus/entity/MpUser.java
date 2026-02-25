package com.duanjh.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-06 周五 15:43
 * @Version: v1.0
 * @Description:
 */
@Data
@NoArgsConstructor
@TableName("t_boot_user")
public class MpUser implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    // 表示该字段为非数据库字段
    @TableField(exist = false)
    private String job;

    public MpUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
