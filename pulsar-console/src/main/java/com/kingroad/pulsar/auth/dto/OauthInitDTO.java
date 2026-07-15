package com.kingroad.pulsar.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:15
 * @Version: v1.0
 * @Description: 系统初始化超级管理员及SSO
 */
@Data
public class OauthInitDTO {

    @NotBlank(message = "超管初始密码不能为空")
    private String passwordHash;

    /**
     * 是否开启SSO
     */
    private Boolean oauthEnable = Boolean.FALSE;

    /* ------------- OAuth2认证信息 ------------- */
    private String clientId;

    private String clientSecret;

    private String authUri;

    private String tokenUri;

    private String userInfoUri;

    private String redirectUri;

}
