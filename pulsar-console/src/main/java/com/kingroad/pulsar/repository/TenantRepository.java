package com.kingroad.pulsar.repository;

import com.kingroad.pulsar.domain.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-03 周一 10:51
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Tenant findByTenantCode(String code);

    @Query("FROM Tenant WHERE isDefault = :isDefault")
    Tenant findExistByIsDefault(@Param("isDefault") boolean isDefault);

}
