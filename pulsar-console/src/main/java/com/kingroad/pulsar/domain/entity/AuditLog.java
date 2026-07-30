package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:25
 * @Version: v1.0
 * @Description: 审计⽇志
 */
@Data
@Entity
@Table(name = "t_audit_log")
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 操作⼈ID（SSO ID）
     */
    private String operatorId;

    /**
     * 操作类型 (如 CREATE_TOPIC，DELETE_EVENT)
     */
    private String operationType;

    /**
     * 操作⽬标资源 (如topic://public/default/order)
     */
    private String targetResource;

    /**
     * 操作详情 (变更前/后内容等)
     */
    private String details;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 操作来源IP
     */
    private String sourceIp;

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createAt;

}
