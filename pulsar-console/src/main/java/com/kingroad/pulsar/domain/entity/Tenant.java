package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-03 周一 10:50
 * @Version: v1.0
 * @Description: 租户管理
 */
@Data
@Entity
@Table(name = "t_tenant")
public class Tenant extends BaseAuditEntity{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 租户编码，唯一
     */
    private String tenantCode;

    /**
     * 租户名
     */
    private String tenantName;

    /**
     * 租户描述
     */
    private String description;

    /**
     * 是否默认租户
     */
    private Boolean isDefault = Boolean.FALSE;

    /**
     * 是否启用
     */
    private Boolean isActive = Boolean.TRUE;

}
