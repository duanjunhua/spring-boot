package com.kingroad.pulsar.service;

import com.kingroad.pulsar.common.PageResult;
import com.kingroad.pulsar.domain.entity.SysResource;
import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysUserRole;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.repository.SysRoleRepository;
import com.kingroad.pulsar.repository.SysUserRoleRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 09:19
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
@Transactional(readOnly=true)
public class SysRoleService {

    @Resource
    SysRoleRepository repository;

    @Resource
    SysUserRoleRepository userRoleRepository;

    /**
     * 分页查询
     */
    public PageResult<SysRole> pageAll(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<SysRole> page = repository.findAll(pageable);
        return PageResult.of(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 根据ID获取对象
     */
    public SysRole findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("查询内容不存在"));
    }

    /**
     * 获取所有
     */
    public List<SysRole> findAll(){
        return repository.findAll();
    }

    /**
     * 根据用户ID查询用户角色集合
     * @param userId 用户ID
     */
    public List<SysRole> findByUserId(Long userId) {

        List<SysUserRole> urs = userRoleRepository.findByUserId(userId);

        if(CollectionUtils.isEmpty(urs) || urs.size() == 0) return Collections.emptyList();

        return repository.findAllById(urs.stream().map(SysUserRole::getRoleId).toList());

    }
}
