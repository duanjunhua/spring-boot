package com.kingroad.pulsar.repository;

import com.kingroad.pulsar.domain.entity.SysRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:32
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface SysRoleResourceRepository extends JpaRepository<SysRoleResource,Long> {

    /**
     * 根据用户角色查询
     * @param roleId 角色ID
     */
    List<SysRoleResource> findByRoleId(Long roleId);

}
