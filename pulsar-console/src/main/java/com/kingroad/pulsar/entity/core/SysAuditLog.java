package com.kingroad.pulsar.entity.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 14:41
 * @Version: v1.0
 * @Description: 审计⽇志
 */
@Data
@TableName("t_audit_log")
public class SysAuditLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作⼈ID（SSO ID）
     */
    private String operatorId;

    /**
     * 操作类型 (如 CREATE_TOPIC，DELETE_EVENT)
     */
    private String operatorType;

    /**
     * 操作⽬标资源 (如topic://public/default/order)
     */
    private String targetResource;

    /**
     * 操作详情 (变更前/后内容等)
     */
    private String details;

    /**
     * 操作来源IP
     */
    private String sourceIp;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

}
