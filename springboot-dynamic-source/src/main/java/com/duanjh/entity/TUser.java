package com.duanjh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-19 周二 10:54
 * @Version: v1.0
 * @Description:
 */
@Data
@TableName("t_uum_user")
public class TUser {

    @TableId(type = IdType.AUTO)
    private String userId;

    private String userLoginName;
}
