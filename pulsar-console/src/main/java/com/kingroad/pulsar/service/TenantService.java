package com.kingroad.pulsar.service;

import com.kingroad.pulsar.common.PageResult;
import com.kingroad.pulsar.domain.entity.Tenant;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.repository.TenantRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-03 周一 10:52
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class TenantService {

    @Resource
    TenantRepository repository;

    /**
     * 分页查询
     */
    public PageResult<Tenant> pageAll(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Tenant> page = repository.findAll(pageable);
        return PageResult.of(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 新增修改对象
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public Tenant saveOrUpdate(Tenant entity) {
        return repository.save(entity);
    }

    /**
     * 根据ID获取对象
     */
    public Tenant findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("查询内容不存在"));
    }

    /**
     * 获取所有
     */
    public List<Tenant> findAll(){
        return repository.findAll();
    }

    /**
     * 新增修改对象列表
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public List<Tenant> saveOrUpdateAll(List<Tenant> entities) {
        return repository.saveAllAndFlush(entities);
    }

    /**
     * 根据租户编码查询租户
     */
    public Tenant queryTenantByTenantCode(String tenantCode) {
        return repository.findByTenantCode(tenantCode);
    }
}
