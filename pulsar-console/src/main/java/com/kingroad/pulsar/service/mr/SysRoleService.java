package com.kingroad.pulsar.service.mr;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kingroad.pulsar.entity.mr.SysRole;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 14:52
 * @Version: v1.0
 * @Description:
 */
public interface SysRoleService extends IService<SysRole> {

    // 根据用户ID查询角色编码
    List<String> getUserRoleCodeList(Long userId);

    List<Long> getRoleIdsByUserId(Long userId);

    void saveUserRole(Long userId, List<Long> roleIdList);

    void saveRoleMenu(Long roleId, List<Long> menuIdList);

    List<Long> getMenuIdsByRoleId(Long roleId);

}
