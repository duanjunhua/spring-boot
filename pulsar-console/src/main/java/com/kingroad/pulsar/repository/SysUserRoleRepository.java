package com.kingroad.pulsar.repository;

import com.kingroad.pulsar.domain.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
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

    List<SysUserRole> findByUserId(Long userId);

}
