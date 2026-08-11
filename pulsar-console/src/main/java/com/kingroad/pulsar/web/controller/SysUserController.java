package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.common.PageQuery;
import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.config.RsaConfig;
import com.kingroad.pulsar.domain.dto.PasswordResetDto;
import com.kingroad.pulsar.domain.entity.SysResource;
import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.repository.SysUserRepository;
import com.kingroad.pulsar.service.SysResourceService;
import com.kingroad.pulsar.service.SysUserService;
import com.kingroad.pulsar.util.EncryptUtil;
import com.kingroad.pulsar.util.SecurityUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Resource
    SysResourceService resService;

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('user:all')")
    public Result<List<SysUser>> all() {
        return super.all();
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('user:page')")
    public Result<Page<SysUser>> page(PageQuery pageQuery) {
        return super.page(pageQuery);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('user:get')")
    public Result<SysUser> getById(Long id) {
        return super.getById(id);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('user:edit')")
    public Result<SysUser> update(Long id, SysUser entity) {
        return super.update(id, entity);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('user:del')")
    public Result<Void> delete(Long id) {
        return super.delete(id);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('user:batch-del')")
    public Result<Void> batchDelete(Iterable<Long> idList) {
        return super.batchDelete(idList);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('user:add')")
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
    @PreAuthorize("@permissionCheck.hasPerm('user:status')")
    @PostMapping("/change-status")
    public Result<SysUser> changeStatus(@RequestParam Long id, @RequestParam Boolean enable) {
        return Result.success(service.changeStatus(id, enable));
    }

    /**
     * 分配角色
     * @param userId 用户ID
     * @param roleIds 分配的角色ID，多个以英文逗号“,”分割
     */
    @PreAuthorize("@permissionCheck.hasPerm('user:assign')")
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

    @PreAuthorize("@permissionCheck.hasPerm('resetPwd')")
    @PostMapping("/reset-password")
    public Result<Void> resetPassword(@RequestBody PasswordResetDto dto) {
        if(ObjectUtils.isEmpty(dto) || ObjectUtils.isEmpty(dto.getUserId()) ||  ObjectUtils.isEmpty(dto.getNewPassword())) {
            return Result.error("用户ID不存在或新密码为空");
        }

        String rawPassword = EncryptUtil.decryptWithRsa(dto.getNewPassword(), EncryptUtil.getRsaPrivateKey(RsaConfig.PRIVATE_KEY));

        service.resetPassword(dto.getUserId(), encoder.encode(rawPassword));

        return Result.success();
    }

    /**
     * 获取当前登录用户权限集合
     */
    @GetMapping("/logged-in/permissions")
    public Result<List<SysResource>> findLoginUserPermissions(){
        SysUser loginUser = SecurityUtil.getLoginUser();
        if(ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getResList())) {
            return Result.success(Collections.EMPTY_LIST);
        }

        // 获取最新用户权限
        loginUser = service.findEntityById(loginUser.getId());

        if(loginUser.getIsSuperAdmin()){
            loginUser.setResList(resService.findAll());
        }

        return Result.success(loginUser.getResList());
    }
}
