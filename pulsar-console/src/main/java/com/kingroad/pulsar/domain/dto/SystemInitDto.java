package com.kingroad.pulsar.domain.dto;

import com.kingroad.pulsar.common.CommonConst;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-04 周二 10:58
 * @Version: v1.0
 * @Description:
 */
@Data
public class SystemInitDto {

    @NotBlank(message = "密码不能为空")
    private String passwordHash;

    private String confirmPassword;

    // 管理员信息
    @NotBlank(message = "管理员用户名不能为空")
    private String username = "superadmin";

    private String chineseName = "超级管理员";

    private String tenantName = CommonConst.DEFAULT_TENANT;

    private Long clusterId = CommonConst.DEFAULT_CLUSTER_ID;

    private Boolean isSuperAdmin = Boolean.TRUE;

    private Boolean enable = Boolean.TRUE;

    // SSO配置
    private Boolean ssoEnable;
    private String registrationId;
    private String clientId;
    private String clientSecret;
    private String authorizationUri;
    private String tokenUri;
    private String redirectUri;
    private String jwtSetUri;
}
