package com.duanjh.oauth2.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-03 周四 16:36
 * @Version: v1.0
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int state;

    private String resource;

    private String expression;

    private Long menuId;

    @Transient
    private String sn;

}
