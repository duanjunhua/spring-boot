package com.duanjh.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-05 周四 16:35
 * @Version: v1.0
 * @Description:
 */
@Data
@Entity(name = "t_boot_user")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)      // 注册实体监听器，用于自动填充审计字段
public class BootUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 200)
    private String password;

    @Column(nullable = true, unique = true, length = 200)
    private String email;

    @CreationTimestamp
    @Column(updatable = false)    // 标注只在创建时设置
    private LocalDateTime createTime;

    @CreatedBy
    @Column(updatable = false)  // 标注只在创建时设置
    private String createBy;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @LastModifiedBy
    private String updateBy;

    // ...

    @Transient
    private String job;

    public BootUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
