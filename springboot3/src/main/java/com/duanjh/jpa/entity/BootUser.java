package com.duanjh.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-05 周四 16:35
 * @Version: v1.0
 * @Description:
 */
@Data
@Entity
@NoArgsConstructor
public class BootUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 200)
    private String password;

    @Column(nullable = true, unique = true, length = 200)
    private String email;

    // ...

    @Transient
    private String job;

    public BootUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
