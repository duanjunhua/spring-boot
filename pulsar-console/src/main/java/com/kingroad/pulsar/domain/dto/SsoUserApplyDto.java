package com.kingroad.pulsar.domain.dto;

import com.kingroad.pulsar.domain.entity.SsoUserApply;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 14:43
 * @Version: v1.0
 * @Description:
 */
@Data
public class SsoUserApplyDto {

    private Long id;

    /**
     * 用户唯一ID
     */
    private Long userId;

    /**
     * 申请理由
     */
    private String applyReason;

    /**
     * 审批状态
     */
    private String status;

    /**
     * 审批意见
     */
    private String opinion;


}
