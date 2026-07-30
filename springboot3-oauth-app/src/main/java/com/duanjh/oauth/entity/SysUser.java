package com.duanjh.oauth.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 15:48
 * @Version: v1.0
 * @Description: 用户实体
 */

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String realName;

    private String email;

    private Boolean enabled;

    private Boolean accountNonExpired;

    private Boolean credentialsNonExpired;

    private Boolean accountNonLocked;

}