package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.common.PageQuery;
import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.domain.entity.SysResource;
import com.kingroad.pulsar.repository.SysResourceRepository;
import com.kingroad.pulsar.service.SysResourceService;
import com.kingroad.pulsar.util.TreeUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:25
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/oop-res")
public class SysResourceController extends BaseCrudController<SysResource, Long, SysResourceRepository> {

    public SysResourceController(SysResourceRepository repository) {
        super(repository);
    }

    @Resource
    SysResourceService service;

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('res:all')")
    public Result<List<SysResource>> all() {
        return super.all();
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('res:page')")
    public Result<Page<SysResource>> page(PageQuery pageQuery) {
        return super.page(pageQuery);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('res:get')")
    public Result<SysResource> getById(Long id) {
        return super.getById(id);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('res:add')")
    public Result<SysResource> save(SysResource entity) {
        return super.save(entity);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('res:edit')")
    public Result<SysResource> update(Long id, SysResource entity) {
        return super.update(id, entity);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('res:del')")
    public Result<Void> delete(Long id) {
        return super.delete(id);
    }

    @Override
    @PreAuthorize("@permissionCheck.hasPerm('res:batch-del')")
    public Result<Void> batchDelete(Iterable<Long> idList) {
        return super.batchDelete(idList);
    }

    @GetMapping("/tree")
    public Result<List<SysResource>> tree() {

        List<SysResource> res = service.getAllResource();

        if(CollectionUtils.isEmpty(res)){
            return Result.success(Collections.EMPTY_LIST);
        }

        List<SysResource> treeRes = TreeUtil.buildTree(res, SysResource::getId, SysResource::getParentId,
                SysResource::getChildren, SysResource::setChildren,
                null,Comparator.comparing(SysResource::getSortOrder));

        return Result.success(treeRes);
    }
}
