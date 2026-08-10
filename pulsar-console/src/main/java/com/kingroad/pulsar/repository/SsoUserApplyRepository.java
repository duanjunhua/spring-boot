package com.kingroad.pulsar.repository;

import com.kingroad.pulsar.domain.entity.SsoUserApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 14:32
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface SsoUserApplyRepository extends JpaRepository<SsoUserApply, Long> {

    List<SsoUserApply> findSsoUsersApplyBySsoId(String ssoId);

}
