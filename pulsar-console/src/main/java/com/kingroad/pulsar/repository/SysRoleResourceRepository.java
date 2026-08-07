package com.kingroad.pulsar.repository;

import com.kingroad.pulsar.domain.entity.SysRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * 根据角色ID清空关联资源
     */
    @Modifying
    void deleteAllByRoleId(Long roleId);

}
