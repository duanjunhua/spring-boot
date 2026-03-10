package com.duanjh.jpa.repository;

import com.duanjh.jpa.entity.BootUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-05 周四 16:37
 * @Version: v1.0
 * @Description:
 */
@Repository
public interface BootUserRepository extends JpaRepository<BootUser, Long> {

    BootUser findByUsername(String username);

    /**
     * 原生SQL，增删改操作需要添加事务@Transactional与可变@Modifying
     */
    @Transactional
    @Modifying
    @Query(value = "delete from t_boot_user where username like concat(:username, '%')  ", nativeQuery = true)
    void deleteByUsername(@Param("username") String username);

    /**
     * JPQL
     */
    @Query("select u from BootUser u where u.username = ?1 and u.email = ?2 ")
    BootUser findByUserNameEamil(String username, String email);


    /**
     * JPQL
     */
    @Query(
            value = "select * from t_boot_user where id > ?1",
            countQuery = "select count(*) from t_boot_user where id > ?1",
            nativeQuery = true
    )
    Page<BootUser> pageQuery(Long id, Pageable pageable);

    /**
     * 使用first或top关键字限制查询方法的结果, First或Top跟一个数字，默认为1
     */
    List<BootUser> findTop3ByPassword(String password, Sort sort);

    /**
     * 当查询没有产生结果时返回null
     */
    @Nullable
    BootUser findFirstByOrderByUsernameDesc();

    /**
     * 流式查询
     */
    Stream<BootUser> findAllByEmailNotNull();

    /**
     * 异步查询Future、CompletableFuture、ListenableFuture
     */
    @Async
    Future<BootUser> findOneByUsername(String username);

}
