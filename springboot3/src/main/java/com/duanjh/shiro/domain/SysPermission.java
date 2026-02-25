package com.duanjh.shiro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-11 周三 16:39
 * @Version: v1.0
 * @Description: 权限信息
 */
@Data
@Entity
@Table(name = "sys_permission")
@NoArgsConstructor
@AllArgsConstructor
public class SysPermission implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /**
     * 资源类型
     */
    @Column(columnDefinition = "enum('menu', 'button')")
    private String resourceType;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 权限字符串：
     *      menu例子：role:*
     *      button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;

    /**
     * 父编号
     */
    private Long parentId;

    /**
     * 父编号列表
     */
    private String parentIds;

    private Boolean available = Boolean.FALSE;


}
