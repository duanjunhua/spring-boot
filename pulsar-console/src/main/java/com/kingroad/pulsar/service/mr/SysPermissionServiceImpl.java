package com.kingroad.pulsar.service.mr;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingroad.pulsar.entity.mr.SysPermission;
import com.kingroad.pulsar.mapper.SysPermissionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 15:20
 * @Version: v1.0
 * @Description:
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Resource
    private SysPermissionMapper mapper;

    @Override
    public List<String> getPermsByRoleCodes(List<String> roleCodes) {
        return ObjectUtil.isNull(roleCodes) || roleCodes.isEmpty() ? List.of() : mapper.selectPermsByRoleCodes(roleCodes);
    }

    @Override
    public List<SysPermission> getAllTreeMenu() {
        return mapper.selectAllMenuTree();
    }
}
