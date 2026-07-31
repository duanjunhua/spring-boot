package com.duanjh.oauth.dto;

import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 16:12
 * @Version: v1.0
 * @Description: 客户端
 */
@Data
public class ClientDTO {

    private String registrationId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private String clientId;

    private String clientSecret;

    private String clientName;

    private String redirectUri;

    private String scope;

}
