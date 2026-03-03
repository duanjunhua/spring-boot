package com.duanjh.jpa.repository;

import com.duanjh.jpa.entity.BootUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-05 周四 16:37
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface BootUserReposotory extends JpaRepository<BootUser, Long> {

    BootUser findByUsername(String username);

    BootUser findByUsernameOrEmail(String username, String email);

    @Transactional
    @Modifying
    @Query(value = "delete from t_boot_user where username like concat(:username, '%')  ", nativeQuery = true)
    void deleteByUsername(@Param("username") String username);

}
