package com.kingroad.pulsar.entity.mr;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 14:48
 * @Version: v1.0
 * @Description: 用户角色
 */
@Data
@TableName("t_role")
public class SysRole {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * ⻆⾊名称（如“系统管理员”、“运维⼈员”、“只读访客”）
     */
    private String roleName;

    /**
     * ⻆⾊唯⼀标识码（如 admin , operator ），⽤于程序逻辑判断
     */
    private String roleCode;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

}
