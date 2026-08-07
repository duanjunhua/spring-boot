package com.kingroad.pulsar.service;

import com.kingroad.pulsar.common.PageResult;
import com.kingroad.pulsar.domain.entity.SysResource;
import com.kingroad.pulsar.domain.entity.SysRoleResource;
import com.kingroad.pulsar.domain.entity.SysUserRole;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.repository.SysResourceRepository;
import com.kingroad.pulsar.repository.SysRoleResourceRepository;
import com.kingroad.pulsar.repository.SysUserRoleRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 09:11
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class SysResourceService {

    @Resource
    SysResourceRepository repository;

    @Resource
    SysRoleResourceRepository roleResRepository;

    @Resource
    SysUserRoleRepository userRoleRepository;

    /**
     * 分页查询
     */
    public PageResult<SysResource> pageAll(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<SysResource> page = repository.findAll(pageable);
        return PageResult.of(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 根据ID获取对象
     */
    public SysResource findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("查询内容不存在"));
    }

    /**
     * 新增修改对象
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public SysResource saveOrUpdate(SysResource entity) {
        return repository.save(entity);
    }

    /**
     * 获取所有
     */
    public List<SysResource> findAll(){
        return repository.findAll();
    }

    /**
     * 根据角色查询资源集合
     * @param roleId 角色ID
     */
    public List<SysResource> findByRoleId(Long roleId){

        List<SysRoleResource> lstRoleRes = roleResRepository.findByRoleId(roleId);

        if(CollectionUtils.isEmpty(lstRoleRes) || lstRoleRes.size() == 0) return Collections.emptyList();

        return repository.findAllById(lstRoleRes.stream().map(SysRoleResource::getResourceId).toList());
    }

    /**
     * 根据角色查询资源集合
     * @param userId 用户ID
     */
    public List<SysResource> findByUserId(Long userId){

        List<SysUserRole> userRoles = userRoleRepository.findByUserId(userId);

        if(CollectionUtils.isEmpty(userRoles) || userRoles.size() == 0) return Collections.emptyList();

        List<SysResource> result = new ArrayList<>();
        userRoles.forEach(ur -> {
            List<SysResource> resList = findByRoleId(ur.getRoleId());
            if(CollectionUtils.isNotEmpty(resList)) result.addAll(resList);
        });

        // 返回根据资源ID去除重复的资源集合
        return CollectionUtils.isEmpty(result) ? Collections.emptyList()
                : result.stream().collect(Collectors.collectingAndThen(
                        // 通过 TreeSet 去重
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SysResource::getId))), ArrayList::new));
    }

    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * 获取所有资源（树形弹窗分配）
     */
    public List<SysResource> getAllResource() {
        return repository.findAllByOrderBySortOrder();
    }

    /**
     * 查询子资源
     */
    public List<SysResource> getChildren(Long parentId) {
        return repository.findByParentIdOrderBySortOrder(parentId);
    }

}
