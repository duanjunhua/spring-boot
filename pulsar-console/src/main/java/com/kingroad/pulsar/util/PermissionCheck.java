package com.kingroad.pulsar.util;

import cn.hutool.core.collection.CollUtil;
import com.kingroad.pulsar.domain.entity.SysResource;
import com.kingroad.pulsar.domain.entity.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-11 周二 10:14
 * @Version: v1.0
 * @Description:
 */
@Component("permissionCheck")
public class PermissionCheck {

    /**
     * 判断是否拥有该功能权限
     */
    public static boolean hasPerm(String perm) {
        SysUser loginUser = SecurityUtil.getLoginUser();

        if(ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getResList())) return false;

        List<String> perms = loginUser.getResList().stream().map(SysResource::getResourceCode).collect(Collectors.toList());

        return CollUtil.contains(perms, perm);
    }

    public static void main(String[] args) {
        List<String> perms = Arrays.asList("res:add", "res:edit", "user:add", "user:edit");
        System.out.println(CollUtil.contains(perms, "res:add"));
    }
}
