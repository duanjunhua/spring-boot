package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.domain.entity.SysResource;
import com.kingroad.pulsar.repository.SysResourceRepository;
import com.kingroad.pulsar.service.SysResourceService;
import com.kingroad.pulsar.util.TreeUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
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
