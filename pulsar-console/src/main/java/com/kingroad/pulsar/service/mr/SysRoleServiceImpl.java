package com.kingroad.pulsar.service.mr;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingroad.pulsar.entity.mr.SysRole;
import com.kingroad.pulsar.entity.mr.SysRolePermission;
import com.kingroad.pulsar.entity.mr.SysUserRole;
import com.kingroad.pulsar.mapper.SysRoleMapper;
import com.kingroad.pulsar.mapper.SysRolePermissionMapper;
import com.kingroad.pulsar.mapper.SysUserRoleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 15:16
 * @Version: v1.0
 * @Description:
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysRolePermissionMapper rolePermissionMapper;

    @Override
    public List<String> getUserRoleCodeList(Long userId) {
        return ObjectUtil.isNull(userId) ? Collections.emptyList() : roleMapper.selectRoleCodeByUserId(userId);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return  ObjectUtil.isNull(userId) ? Collections.emptyList() : userRoleMapper.selectRoleIdsByUserId(userId);
    }

    /**
     * 保存用户-角色关联，先删后新增
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserRole(Long userId, List<Long> roleIdList) {
        // 删除原有关联
        userRoleMapper.deleteByUserId(userId);

        if(CollUtil.isEmpty(roleIdList)) return;

        // 批量新增
        List<SysUserRole> list = new ArrayList<>();
        for(Long rid : roleIdList){
            SysUserRole ur = SysUserRole.builder().userId(userId).roleId(rid).build();
            list.add(ur);
        }
        userRoleMapper.batchInsert(list);
    }

    /**
     * 角色分配菜单权限
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRoleMenu(Long roleId, List<Long> menuIdList) {
        rolePermissionMapper.deleteByRoleId(roleId);
        if(CollUtil.isEmpty(menuIdList)) return;
        List<SysRolePermission> list = new ArrayList<>();
        for(Long mid : menuIdList){
            SysRolePermission rm = new SysRolePermission();
            rm.setRoleId(roleId);
            rm.setMenuId(mid);
            list.add(rm);
        }
        rolePermissionMapper.batchInsert(list);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return ObjectUtil.isNull(roleId) ? Collections.emptyList() : rolePermissionMapper.selectMenuIdsByRoleId(roleId);
    }
}
