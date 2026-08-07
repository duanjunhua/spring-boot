package com.kingroad.pulsar.repository;

import com.kingroad.pulsar.domain.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 16:32
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    Optional<SysUser> findByUsername(String username);

    Optional<SysUser> findBySsoId(String ssoId);

}
