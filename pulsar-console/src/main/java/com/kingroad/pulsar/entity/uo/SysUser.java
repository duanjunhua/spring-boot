package com.kingroad.pulsar.entity.uo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:14
 * @Version: v1.0
 * @Description:
 */
@Data
@TableName("t_user")
public class SysUser {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * SSO 平台的唯⼀⽤⼾ID
     */
    private String userId;

    /**
     * ⽤⼾显⽰名
     */
    private String username;

    /**
     * ⽤⼾中文名
     */
    private String chineseName;

    /**
     * 对应 Pulsar 租⼾名，全局唯⼀
     */
    private String tenantName;

    /**
     * 关联的 Pulsar 集群ID (外键)
     */
    private Integer pulsarClusterId;

    /**
     * 是否为超级管理员
     */
    private Boolean isSuperAdmin =  Boolean.FALSE;

    /**
     * 仅超级管理员使⽤，MD5 加密后的密码摘要
     */
    private String passwordHash;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;


}
