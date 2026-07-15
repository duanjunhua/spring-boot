package com.kingroad.pulsar.service.uo;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingroad.pulsar.entity.uo.SysUser;
import com.kingroad.pulsar.mapper.SysUserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:27
 * @Version: v1.0
 * @Description:
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper userMapper;

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);

        return getOne(wrapper);
    }

    @Override
    public void updateLastLogin(String username) {
        SysUser user = getByUsername(username);
        if(ObjectUtil.isNull(user)) return;
        user.setLastLoginTime(LocalDateTime.now());
        updateById(user);
    }
}
