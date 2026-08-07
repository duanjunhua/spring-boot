package com.kingroad.pulsar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:49
 * @Version: v1.0
 * @Description: 资源
 */
@Data
@Entity
@Table(name = "t_permission")
public class SysResource extends BaseAuditEntity{

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限名称（如“⽤⼾管理”、“新增⽤⼾”）
     */
    @Column(name = "permission_name")
    private String resourceName;

    /**
     * 权限唯⼀标识码（如 user:read , user:create ），⽤于前后端鉴权
     */
    @Column(name = "permission_code")
    private String resourceCode;

    /**
     * ⽗级权限ID（外键⾃关联），为 NULL 时表⽰顶级权限
     */
    private Long parentId;

    /**
     * 资源类型（如 menu 菜单, button 按钮, api 接⼝）
     */
    private String resourceType = ResourceType.other.name();

    /**
     * 资源路径（如前端路由 /user/list 或后端接⼝/api/v1/users ）
     */
    private String resourcePath;

    /**
     * 顺序，⽤于前端菜单或权限列表的展⽰顺序
     */
    private Integer sortOrder;

    /**
     * 是否启用
     */
    private Boolean enable = Boolean.TRUE;

    /**
     * 资源类型
     */
    public enum ResourceType{
        content, menu, button, api, other
    }

    /**
     * 子（下级）资源
     */
    @Transient
    public List<SysResource> children;
}
