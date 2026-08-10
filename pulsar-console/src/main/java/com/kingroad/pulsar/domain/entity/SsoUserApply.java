package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 14:10
 * @Version: v1.0
 * @Description: 第三方用户开通申请
 */
@Data
@Entity
@Table(name = "t_sso_user_apply")
public class SsoUserApply extends BaseAuditEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户第三方ID
     */
    private String ssoId;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 申请理由
     */
    private String applyReason;

    /**
     * 审批状态
     */
    private String status =  ApplyStatus.APPLYING.name();

    /**
     * 审批意见
     */
    private String approvalOpinion;

    /**
     * 审批人
     */
    @LastModifiedBy
    private Long changedBy;

    public enum ApplyStatus {

        // 审批中
        APPLYING,
        // 已批准
        APPROVED,
        // 已驳回
        REJECTED
    }
}
