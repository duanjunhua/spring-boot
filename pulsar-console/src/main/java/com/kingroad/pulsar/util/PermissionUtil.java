package com.kingroad.pulsar.util;

import cn.hutool.core.collection.CollUtil;
import com.kingroad.pulsar.auth.bo.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 14:59
 * @Version: v1.0
 * @Description: 权限检查
 */
@Component
public class PermissionUtil {

    /**
     * 判断是否拥有该功能权限
     */
    public static boolean hasPerm(String perm) {
        LoginUser loginUser = getLoginUser();
        if(loginUser == null) return false;

        List<String> perms = loginUser.getPerms();
        return CollUtil.contains(perms, perm);
    }

    public static LoginUser getLoginUser() {
        Object authObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(authObj instanceof LoginUser loginUser){
            return loginUser;
        }

        return null;
    }

}
