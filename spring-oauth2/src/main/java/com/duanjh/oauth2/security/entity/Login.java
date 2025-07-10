package com.duanjh.oauth2.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-03 周四 16:22
 * @Version: v1.0
 * @Description:
 */
@Data
@Entity(name = "t_login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

}
