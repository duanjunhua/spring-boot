package com.kingroad.pulsar.entity.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kingroad.pulsar.constant.StatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 14:29
 * @Version: v1.0
 * @Description: 端凭证表
 */
@Data
@TableName("t_event_definition")
public class BusClientCredential {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 端名称
     */
    private String name;

    /**
     * 对应 Pulsar ⻆⾊名 (同⽤⼾下唯⼀)
     */
    private String roleName;

    /**
     * 所属⽤⼾ID (外键)'
     */
    private Long userId;

    /**
     * JWT 访问凭证
     */
    private String jwtToken;

    /**
     * 状态 (ENABLED/DISABLED)
     */
    private String status = StatusEnum.ENABLED.name();

    /**
     * 凭证过期时间
     */
    private LocalDateTime expiresAt;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;
}
