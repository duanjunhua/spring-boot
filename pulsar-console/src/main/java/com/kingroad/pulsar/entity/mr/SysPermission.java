package com.kingroad.pulsar.entity.mr;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 14:49
 * @Version: v1.0
 * @Description: 权限资源
 */
@Data
@TableName("t_permission")
public class SysPermission {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称（如“⽤⼾管理”、“新增⽤⼾”）
     */
    private String permissionName;

    /**
     * 权限唯⼀标识码（如 user:list、user:add ），⽤于前后端鉴权
     */
    private String permissionCode;

    /**
     * ⽗级权限ID（外键⾃关联），为 NULL 时表⽰顶级权限
     */
    private Long parentId;

    /**
     * 资源类型：MenuType（如 menu 菜单, button 按钮, api 接⼝
     */
    private String resourceType;

    /**
     * 资源路径（如前端路由 /user/list 或后端接⼝/api/v1/users）
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
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    private LocalDateTime updateAt;

}
