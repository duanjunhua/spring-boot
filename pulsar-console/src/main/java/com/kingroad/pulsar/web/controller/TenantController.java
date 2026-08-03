package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.domain.entity.Tenant;
import com.kingroad.pulsar.repository.TenantRepository;
import com.kingroad.pulsar.service.TenantService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-03 周一 11:08
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/pulsar-tenant")
public class TenantController extends BaseCrudController<Tenant, Long, TenantRepository>{

    protected TenantController(TenantRepository repository) {
        super(repository);
    }

    @Resource
    TenantService service;

    /**
     * 修改
     */
    @PostMapping("/set-default/{id}")
    public Result<Tenant> setDefault(@PathVariable Long id) {
        Optional<Tenant> optional = repository.findById(id);
        if (!optional.isPresent()) {
            return Result.error("数据不存在，无法修改");
        }
        List<Tenant> clusters = repository.findAll();
        clusters.forEach(cluster -> {
            if(cluster.getId() == id) {
                cluster.setIsDefault(true);
            }else {
                cluster.setIsDefault(false);
            }
        });

        service.saveOrUpdateAll(clusters);

        return Result.success(optional.get());
    }

    /**
     * 修改
     */
    @PostMapping("/change-active")
    public Result<Tenant> changeStatus(Long id, Boolean isActive) {
        Optional<Tenant> optional = repository.findById(id);
        if (!optional.isPresent()) {
            return Result.error("数据不存在，无法修改");
        }

        Tenant cluster = optional.get();
        cluster.setIsActive(isActive);
        repository.save(cluster);

        return Result.success(cluster);
    }

    /**
     * 校验租户编号是否已存在
     */
    @PostMapping("/exist")
    public Result<Boolean> exist(String tenantCode) {
        Tenant tenant = service.queryTenantByTenantCode(tenantCode);
        if (ObjectUtils.isNotEmpty(tenant)) {
            return Result.success("租户编码已存在", true);
        }
        return Result.success(false);
    }

    /**
     * 校验默认租户是否已存在
     */
    @PostMapping("/has-default")
    public Result<Boolean> haDefault() {
        Tenant tenant = repository.findExistByIsDefault(true);
        if (ObjectUtils.isNotEmpty(tenant)) {
            return Result.success("默认租户已存在", true);
        }
        return Result.success(false);
    }

}
