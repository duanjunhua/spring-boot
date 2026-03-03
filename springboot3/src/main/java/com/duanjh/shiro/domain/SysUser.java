package com.duanjh.shiro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-11 周三 16:27
 * @Version: v1.0
 * @Description: 用户信息
 */
@Data
@Entity
@Table(name = "sys_user")
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 中文名
     */
    private String nickName;

    /**
     * 登录名
     */
    @Column(unique=true)
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密密码的盐
     */
    private String salt;

    /**
     * 用户状态（0：未认证、1：正常状态、2：用户锁定）
     */
    private byte state;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Transient
    private List<SysRole> roles = new ArrayList<>();

    public String getCredentialsSalt() {
        return this.username + this.salt;
    }

    public SysUser(Long id, String nickName, String username, String password) {
        this.id = id;
        this.nickName = nickName;
        this.username = username;
        this.password = password;
    }
}
