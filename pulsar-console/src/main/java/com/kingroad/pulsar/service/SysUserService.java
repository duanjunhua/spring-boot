package com.kingroad.pulsar.service;

import com.kingroad.pulsar.common.PageResult;
import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.repository.SysUserRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 09:26
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class SysUserService {

    @Resource
    SysUserRepository repository;

    @Resource
    SysRoleService roleService;

    @Resource
    SysResourceService resService;

    /**
     * 分页查询
     */
    public PageResult<SysUser> pageAll(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<SysUser> page = repository.findAll(pageable);
        if(CollectionUtils.isEmpty(page.getContent())) return PageResult.of(Collections.emptyList(), page.getTotalElements(), pageNum, pageSize);

        page.getContent().forEach(u -> {
            u.setRoleList(roleService.findByUserId(u.getId()));
            u.setResList(resService.findByUserId(u.getId()));
        });

        return PageResult.of(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 新增修改用户
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public SysUser saveOrUpdate(SysUser entity) {
        return repository.save(entity);
    }

    /**
     * 根据ID获取对象
     */
    public SysUser findEntityById(Long id) {
        Optional<SysUser> user = repository.findById(id);
        if(user.isPresent()) {
            SysUser u =  user.get();
            u.setResList(resService.findByUserId(u.getId()));
            u.setRoleList(roleService.findByUserId(u.getId()));
            return u;
        }
        return null;
    }

    /**
     * 根据用户名查询
     * @param username 用户登录名
     */
    public SysUser findEntityByUsername(String username) {
        SysUser u = repository.findByUsername(username);
        if(ObjectUtils.isEmpty(u)) return null;

        u.setResList(resService.findByUserId(u.getId()));
        u.setRoleList(roleService.findByUserId(u.getId()));
        return u;
    }

    /**
     * 根据用户名查询
     * @param ssoId sso ID
     */
    public SysUser findEntityBySsoId(String ssoId) {
        SysUser u = repository.findBySsoId(ssoId);

        if(ObjectUtils.isEmpty(u)) return null;

        u.setResList(resService.findByUserId(u.getId()));
        u.setRoleList(roleService.findByUserId(u.getId()));
        return u;
    }


    /**
     * 获取所有
     */
    public List<SysUser> findAll(){
        List<SysUser> us = repository.findAll();
        if(CollectionUtils.isEmpty(us)) return Collections.emptyList();

        us.forEach(u -> {
            u.setRoleList(roleService.findByUserId(u.getId()));
            u.setResList(resService.findByUserId(u.getId()));
        });
        return us;
    }
}
