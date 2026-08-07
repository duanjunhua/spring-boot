package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.config.RsaConfig;
import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.repository.SysUserRepository;
import com.kingroad.pulsar.service.SysUserService;
import com.kingroad.pulsar.util.EncryptUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:27
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/oop-user")
public class SysUserController extends BaseCrudController<SysUser, Long, SysUserRepository> {

    public SysUserController(SysUserRepository repository) {
        super(repository);
    }

    @Resource
    SysUserService service;
    @Resource
    PasswordEncoder encoder;

    @Override
    public Result<SysUser> save(SysUser entity) {

        if(ObjectUtils.isEmpty(entity.getId())) {
            String rawPassword = EncryptUtil.decryptWithRsa(entity.getPasswordHash(), EncryptUtil.getRsaPrivateKey(RsaConfig.PRIVATE_KEY));
            entity.setPasswordHash(encoder.encode(rawPassword));
        }

        return super.save(entity);
    }

    /**
     * 获取用户关联角色ID数组
     */
    @GetMapping("/get-role-ids/{id}")
    public Result<List<Long>> getRolesByUserId(@PathVariable("id") Long userId) {
        if(ObjectUtils.isEmpty(userId)) {
            return Result.success(Collections.EMPTY_LIST);
        }

        SysUser u = service.findEntityById(userId);

        if(ObjectUtils.isEmpty(u) || CollectionUtils.isEmpty(u.getRoleList())) {
            return Result.success(Collections.EMPTY_LIST);
        }

        return Result.success(u.getRoleList().stream().map(SysRole::getId).toList());
    }

    /**
     * 修改启用状态
     */
    @PostMapping("/change-status")
    public Result<SysUser> changeStatus(@RequestParam Long id, @RequestParam Boolean enable) {
        return Result.success(service.changeStatus(id, enable));
    }

    /**
     * 分配角色
     * @param userId 用户ID
     * @param roleIds 分配的角色ID，多个以英文逗号“,”分割
     */
    @PostMapping("/assign-role")
    public Result<Void> assignRole(@RequestParam Long userId, @RequestParam String roleIds) {
        List<Long> roles = Collections.emptyList();
        if(StringUtils.isNotBlank(roleIds)) {
            roles = Arrays.stream(roleIds.split(","))
                    .map(String::trim)
                    .filter(s -> StringUtils.isNotBlank(s))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        }
        service.assignRole(userId, roles);
        return Result.success();
    }
}
