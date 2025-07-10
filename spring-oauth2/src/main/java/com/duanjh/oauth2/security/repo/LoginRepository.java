package com.duanjh.oauth2.security.repo;

import com.duanjh.oauth2.security.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-03 周四 16:38
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface LoginRepository extends JpaRepository<Login, Login> {

    @Query(value = "select id, username, password from t_login where username = :username", nativeQuery = true)
    Login findByUsername(@Param("username") String username);

}
