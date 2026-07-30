package com.duanjh.oauth.repository;

import com.duanjh.oauth.entity.SysAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 16:57
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface SysAuthorityRepository extends JpaRepository<SysAuthority, Long> {

    List<SysAuthority> findByUserId(Long userId);

}
