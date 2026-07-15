package com.kingroad.pulsar.service.uo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kingroad.pulsar.entity.uo.SysUser;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:22
 * @Version: v1.0
 * @Description:
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    void updateLastLogin(String username);

}
