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
 * @Date: 2026-02-11 周三 16:31
 * @Version: v1.0
 * @Description: 角色信息
 */
@Data
@Entity
@Table(name = "sys_role")
@NoArgsConstructor
@AllArgsConstructor
public class SysRole implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的
     */
    private String role;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 是否可用
     */
    private Boolean available = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Transient
    private List<SysPermission> permissions;

    @Transient
    private List<SysUser> users = new ArrayList<>();
}
