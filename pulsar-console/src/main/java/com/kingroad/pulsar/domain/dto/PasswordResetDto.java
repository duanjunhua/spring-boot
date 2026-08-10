package com.kingroad.pulsar.domain.dto;

import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 10:05
 * @Version: v1.0
 * @Description:
 */
@Data
public class PasswordResetDto {

    private Long userId;

    private String newPassword;

}
