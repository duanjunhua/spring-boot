package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:58
 * @Version: v1.0
 * @Description: 角色
 */
@Data
@Entity
@Table(name = "t_role")
public class SysRole extends BaseAuditEntity{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ⻆⾊名称（如“系统管理员”、“运维⼈员”、“只读访客”）
     */
    private String roleName;

    /**
     * ⻆⾊唯⼀标识码（如 admin , operator ），⽤于程序逻辑判断
     */
    private String roleCode;

    /**
     * ⻆⾊描述信息
     */
    private String description;

    /**
     * 是否系统角色，系统角色不允许删除
     */
    private Boolean isSystemRole = Boolean.FALSE;

}
