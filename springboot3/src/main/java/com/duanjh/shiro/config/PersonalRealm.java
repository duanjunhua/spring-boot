package com.duanjh.shiro.config;

import com.duanjh.shiro.domain.SysPermission;
import com.duanjh.shiro.domain.SysRole;
import com.duanjh.shiro.domain.SysUser;
import com.duanjh.shiro.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.lang.util.ByteSource;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-12 周四 09:51
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Configuration
public class PersonalRealm extends AuthorizingRealm {

    @Autowired
    SysUserMapper sysUserMapper;

    /**
     * 自定义授权
     *    当访问到页面的时候，链接配置了相应的权限或者Shiro标签才会执行此方法否则不会执行
     *    主要是使用类：SimpleAuthorizationInfo进行角色的添加和权限的添加，也可以添加 set 集合：roles 是从数据库查询的当前用户的角色，strPermissions是从数据库查询的当前用户对应的权限
     *      authorization.setRoles(roles);
     *      authorization.setStringPermissions(strPermissions);
     *
     *    如：
     *      filterChainDefinitionMap.put(“/add”, “perms[权限添加]”)表示访问/add这个链接必须要有“权限添加”这个权限才可以访问
     *      filterChainDefinitionMap.put(“/add”, “roles[100002]，perms[权限添加]”)表示访问/add这个链接必须要有“权限添加”这个权限和具有“100002”这个角色才可以访问
     *
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        log.info("权限配置");

        SimpleAuthorizationInfo authorization = new SimpleAuthorizationInfo();

        // 获取当前用户信息
        SysUser user = (SysUser) principals.getPrimaryPrincipal();

        /**
         * 角色及权限配置
         */


        // 如果有管理员角色直接全员
        if(CollectionUtils.isNotEmpty(user.getRoles()) && user.getRoles().parallelStream().filter(r -> "admin".equals(r.getRole())).findAny().isPresent()) {
            authorization.addRole("admin");
            authorization.addStringPermission("*:*:*");
            return authorization;
        }

        //若没有管理员角色则赋予所有角色权限集合
        for (SysRole role : user.getRoles()) {
            authorization.addRole(role.getRole());
            for (SysPermission permission : role.getPermissions()) {
                authorization.addStringPermission(permission.getPermission());
            }
        }

        return authorization;
    }

    /**
     * 自定义认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {

        // 1. 获取用户输入的用户名
        UsernamePasswordToken upToken = (UsernamePasswordToken) authToken;
        String username = upToken.getUsername();

        // 2.根据用户名查询用户
        SysUser user = sysUserMapper.selectByUsername(username);

        log.info("查询到的用户：{}", user);

        // 3. 将查询到的用户封装为认证信息
        if(ObjectUtils.isEmpty(user)){
            throw  new UnknownAccountException("账户不存在");
        }



        return new SimpleAuthenticationInfo(
                user,   // 账号
                user.getPassword(), // 密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),  // 自定义认证盐
                this.getName()  // 当前realm名
        );
    }
}
