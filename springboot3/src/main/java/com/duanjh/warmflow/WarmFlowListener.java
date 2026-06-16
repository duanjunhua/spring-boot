package com.duanjh.warmflow;

import com.duanjh.shiro.domain.SysRole;
import com.duanjh.shiro.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.listener.Listener;
import org.dromara.warm.flow.core.listener.ListenerVariable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-06-02 周二 15:54
 * @Version: v1.0
 * @Description: 流程变量监听器
 */
@Slf4j
@Component
public class WarmFlowListener implements Listener {

    @Override
    public void notify(ListenerVariable variable) {
        log.info("Complete Listener Start: {}", variable);

        Instance instance = variable.getInstance();

        Map<String, Object> variables = variable.getVariable();

        // 处理相关逻辑，如设置用户
        SysUser principal = (SysUser)SecurityUtils.getSubject().getPrincipal();
        FlowParams flowParams = variable.getFlowParams();
        // 设置当前办理人id
        flowParams.handler(principal.getUsername());
        // 设置办理人所拥有的权限，比如角色、部门、用户等
        List<String> permissionList = flowParams.getPermissionFlag();
        if (CollectionUtils.isEmpty(permissionList)) {
            permissionList = new ArrayList<>();
        }

        List<SysRole> roles = principal.getRoles();
        if (Objects.nonNull(roles)) {
            permissionList.addAll(roles.stream().map(role -> "role:" + role.getId()).collect(Collectors.toList()));
        }
        permissionList.add("dept:" + principal.getDeptId());
        permissionList.add(principal.getId().toString());
        flowParams.permissionFlag(permissionList);


        log.info("Complete Listener End...");

    }
}
