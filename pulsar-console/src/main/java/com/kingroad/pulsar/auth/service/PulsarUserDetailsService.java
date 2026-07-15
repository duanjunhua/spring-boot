package com.kingroad.pulsar.auth.service;

import cn.hutool.core.util.ObjectUtil;
import com.kingroad.pulsar.auth.bo.LoginUser;
import com.kingroad.pulsar.entity.uo.SysUser;
import com.kingroad.pulsar.service.mr.SysPermissionService;
import com.kingroad.pulsar.service.mr.SysRoleService;
import com.kingroad.pulsar.service.uo.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:29
 * @Version: v1.0
 * @Description:
 */
@Service
public class PulsarUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserService userService;

    @Resource
    SysRoleService roleService;

    @Resource
    SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.getByUsername(username);
        if(ObjectUtil.isNull(user)) throw new UsernameNotFoundException("用户不存在");

        // 1.获取用户角色
        List<String> roleCodes = roleService.getUserRoleCodeList(user.getId());
        List<GrantedAuthority> authorities = roleCodes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 2.获取按钮功能权限标识
        List<String> permsList = permissionService.getPermsByRoleCodes(roleCodes);

        // 3. 若用户是否时系统超级管理员则赋予全部权限
        if(ObjectUtil.isNotNull(user) && user.getIsSuperAdmin()){
            permsList = permissionService.list().stream().map(m -> m.getPermissionCode()).collect(Collectors.toList());
        }

        // 存入自定义UserDetails扩展权限标识
        LoginUser loginUser = new LoginUser(user, authorities, permsList);

        return loginUser;
    }
}
