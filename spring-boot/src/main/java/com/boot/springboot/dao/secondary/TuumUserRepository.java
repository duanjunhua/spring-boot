package com.boot.springboot.dao.secondary;

import com.boot.springboot.domain.secondary.TuumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-31
 * @Version: V1.0
 * @Description:
 */
@Repository
public interface TuumUserRepository extends JpaRepository<TuumUser, String> {
}
