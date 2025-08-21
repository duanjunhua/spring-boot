package com.duanjh.auth.repo;

import com.duanjh.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-08 周五 16:11
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * 根据用户ID查询用户权限
      */
    @Query(value = "select id, expression from t_permission", nativeQuery = true)
    List<Permission> findByUserId(Long userId);

}
