package com.kingroad.pulsar.service;

import com.kingroad.pulsar.common.PageResult;
import com.kingroad.pulsar.domain.entity.SsoUserApply;
import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.domain.entity.SysUserRole;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.repository.SsoUserApplyRepository;
import com.kingroad.pulsar.repository.SysRoleRepository;
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

import java.util.List;
import java.util.Optional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 14:33
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class SsoUserApplyService {

    @Resource
    SsoUserApplyRepository repository;

    @Resource
    SysRoleRepository roleRepository;

    @Resource
    SysUserRoleRepository urRepository;

    /**
     * 分页查询
     */
    public PageResult<SsoUserApply> pageAll(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<SsoUserApply> page = repository.findAll(pageable);
        return PageResult.of(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 新增修改对象
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public SsoUserApply saveOrUpdate(SsoUserApply entity) {
        return repository.save(entity);
    }

    /**
     * 根据ID获取对象
     */
    public SsoUserApply findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("查询内容不存在"));
    }

    /**
     * 获取所有
     */
    public List<SsoUserApply> findAll(){
        return repository.findAll();
    }

    /**
     * 根据用户SSO唯一ID查询
     */
    public SsoUserApply findApprovedApplyBySsoId(String ssoId) {
        List<SsoUserApply> entities = repository.findSsoUsersApplyBySsoId(ssoId);

        if(CollectionUtils.isEmpty(entities)) return null;

        Optional<SsoUserApply> optional = entities.stream().filter(entity ->
                SsoUserApply.ApplyStatus.APPLYING.name().equals(entity.getStatus()) ||  SsoUserApply.ApplyStatus.APPROVED.name().equals(entity.getStatus())
            ).findAny();

        return optional.orElse(null);
    }

    /**
     * 审批用户申请
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public SsoUserApply auditSsoUserApply(SsoUserApply entity) {

        /**
         * 通过给予用户普通角色
         */
        SysRole role = roleRepository.findByRoleCode("ROLE_USER").orElse(null);
        if(ObjectUtils.isNotEmpty(role) && SsoUserApply.ApplyStatus.APPROVED.name().equals(entity.getStatus())){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(role.getId());
            sysUserRole.setUserId(entity.getUserId());
            urRepository.save(sysUserRole);
        }
        return this.saveOrUpdate(entity);
    }
}
