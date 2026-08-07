package com.kingroad.pulsar.repository;

import com.kingroad.pulsar.domain.entity.SysResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:31
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface SysResourceRepository extends JpaRepository<SysResource,Long> {

    /**
     * 根据父级资源ID查询子资源列表
     * @param parentId 父级资源ID
     */
    List<SysResource> findByParentIdOrderBySortOrder(Long parentId);

    /**
     * 查询所有资源ID
     */
    List<SysResource> findAllByOrderBySortOrder();

}
