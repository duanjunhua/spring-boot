package com.duanjh.auth.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-08 周五 15:55
 * @Version: v1.0
 * @Description: 用户
 */
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

}
