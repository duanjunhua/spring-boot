package com.kingroad.pulsar.service.mr;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kingroad.pulsar.entity.mr.SysPermission;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 14:52
 * @Version: v1.0
 * @Description:
 */
public interface SysPermissionService extends IService<SysPermission> {

    // 根据角色编码查询权限标识 user:list
    List<String> getPermsByRoleCodes(List<String> roleCodes);

    List<SysPermission> getAllTreeMenu();

}
