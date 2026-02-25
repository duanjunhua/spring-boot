package com.duanjh.jpa.repository;

import com.duanjh.jpa.entity.BootUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

}
