package com.kingroad.pulsar.repository;

import com.kingroad.pulsar.domain.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:33
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole,Long>{

    /**
     * 根据用户ID查询用户角色
     */
    List<SysUserRole> findByUserId(Long userId);

    /**
     * 根据角色ID清空关联用户
     */
    @Modifying
    void deleteAllByRoleId(Long roleId);

    /**
     * 根据用户ID清空用户关联角色
     */
    @Modifying
    void deleteAllByUserId(Long userId);

}
