package com.kingroad.pulsar.service;

import com.kingroad.pulsar.common.PageResult;
import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysRoleResource;
import com.kingroad.pulsar.domain.entity.SysUserRole;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.repository.SysRoleRepository;
import com.kingroad.pulsar.repository.SysRoleResourceRepository;
import com.kingroad.pulsar.repository.SysUserRoleRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Resource
    SysRoleResourceRepository roleResRepository;

    /**
     * 分页查询
     */
    public PageResult<SysRole> pageAll(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<SysRole> page = repository.findAll(pageable);
        return PageResult.of(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 新增修改对象
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public SysRole saveOrUpdate(SysRole entity) {
        return repository.save(entity);
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
     * 根据ID获取对象
     */
    public SysRole findEntityByRoleCode(String roleCode) {
        return repository.findByRoleCode(roleCode).orElse(null);
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

    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * 根据角色查询已分配资源
     */
    public List<SysRoleResource> findRoleResByRoleId(Long roleId) {
        return roleResRepository.findByRoleId(roleId);
    }

    /**
     * 角色分配资源
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public void assignResource(Long roleId, List<Long> resIds) {

        if(ObjectUtils.isEmpty(resIds)) return;

        roleResRepository.deleteAllByRoleId(roleId);

        List<SysRoleResource> roleResList = new ArrayList<>(resIds.size());

        if(CollectionUtils.isEmpty(resIds))  return;

        resIds.forEach(resId -> {
            roleResList.add(new SysRoleResource(roleId, resId));
        });

        roleResRepository.saveAll(roleResList);
    }

    /**
     * 角色分配用户
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public void assignUser(Long roleId, List<Long> userIds) {

        if(ObjectUtils.isEmpty(userIds)) return;

        userRoleRepository.deleteAllByRoleId(roleId);

        List<SysUserRole> roleResList = new ArrayList<>(userIds.size());

        if(CollectionUtils.isEmpty(userIds))  return;

        userIds.forEach(uId -> {
            roleResList.add(new SysUserRole(uId, roleId));
        });

        userRoleRepository.saveAll(roleResList);
    }
}
