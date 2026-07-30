package com.duanjh.oauth.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 15:49
 * @Version: v1.0
 * @Description: 权限实体
 */
@Data
@Entity
@Table(name = "sys_authority")
public class SysAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String authority;
}