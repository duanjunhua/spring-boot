package com.duanjh.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duanjh.shiro.domain.SysPermission;
import com.duanjh.shiro.domain.SysRole;
import com.duanjh.shiro.domain.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-12 周四 09:52
 * @Version: v1.0
 * @Description:
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_user where username = #{username}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "salt", column = "salt"),
            @Result(property = "state", column = "state"),
            @Result(property = "roles", column = "id", many = @Many(select = "selectRolesByUserId"))
    })
    SysUser selectByUsername(@Param("username") String username);

    @Select("select r.id, r.role, r.description, r.available from sys_role r left join sys_user_role ur on r.id = ur.role_id where ur.user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "role", column = "role"),
            @Result(property = "description", column = "description"),
            @Result(property = "available", column = "available"),
            @Result(property = "permissions", column = "id", many = @Many(select = "selectPermissionsByRoleId"))
    })
    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);

    @Select("select p.* from sys_permission p left join sys_role_permission rp on p.id = rp.permission_id where rp.role_id = #{roleId} ")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "resourceType", column = "resource_type"),
            @Result(property = "url", column = "url"),
            @Result(property = "permission", column = "permission"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "parentIds", column = "parent_ids"),
            @Result(property = "available", column = "available")
    })
    List<SysPermission> selectPermissionsByRoleId(@Param("roleId") Long roleId);
}
