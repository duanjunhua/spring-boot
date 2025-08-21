package com.duanjh.auth.repo;

import com.duanjh.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-08 周五 16:10
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户登录名查询用户
     */
    @Query(value = "select id, username, password from t_user where username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

}
