package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.common.PageQuery;
import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysRoleResource;
import com.kingroad.pulsar.repository.SysRoleRepository;
import com.kingroad.pulsar.service.SysRoleService;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:26
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/oop-role")
public class SysRoleController extends BaseCrudController<SysRole, Long, SysRoleRepository> {

    public SysRoleController(SysRoleRepository repository) {
        super(repository);
    }

    @Resource
    SysRoleService service;

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('role:all')")
    public Result<List<SysRole>> all() {
        return super.all();
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('role:page')")
    public Result<Page<SysRole>> page(PageQuery pageQuery) {
        return super.page(pageQuery);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('role:get')")
    public Result<SysRole> getById(Long id) {
        return super.getById(id);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('role:add')")
    public Result<SysRole> save(SysRole entity) {
        return super.save(entity);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('role:edit')")
    public Result<SysRole> update(Long id, SysRole entity) {
        return super.update(id, entity);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('role:del')")
    public Result<Void> delete(Long id) {
        return super.delete(id);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('role:batch-del')")
    public Result<Void> batchDelete(Iterable<Long> idList) {
        return super.batchDelete(idList);
    }

    /**
     * 获取用户关联角色ID数组
     */
    @GetMapping("/get-res-ids/{id}")
    public Result<List<Long>> getResByRoleId(@PathVariable("id") Long roleId) {
        if(ObjectUtils.isEmpty(roleId)) {
            return Result.success(Collections.EMPTY_LIST);
        }

        List<SysRoleResource> roleRes = service.findRoleResByRoleId(roleId);

        if(CollectionUtils.isEmpty(roleRes)) {
            return Result.success(Collections.EMPTY_LIST);
        }

        return Result.success(roleRes.stream().map(SysRoleResource::getResourceId).toList());
    }

    @PreAuthorize("@permissionCheck.hasPerm('role:setting')")
    @PostMapping("/assign-res")
    public Result<Void> assignRoleRes(@RequestParam Long roleId, @RequestParam String resIds) {
        List<Long> res = Collections.emptyList();
        if(StringUtils.isNotBlank(resIds)) {
            res = Arrays.stream(resIds.split(","))
                    .map(String::trim)
                    .filter(s -> StringUtils.isNotBlank(s))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        }
        service.assignResource(roleId, res);
        return Result.success();
    }
}
