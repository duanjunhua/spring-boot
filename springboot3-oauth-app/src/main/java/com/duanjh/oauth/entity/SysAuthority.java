package com.duanjh.oauth.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 15:49
 * @Version: v1.0
 * @Description: 权限实体
 */
@Data
@Entity
@Table(name = "sys_authority")
public class SysAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String authority;
}