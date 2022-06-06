package com.boot.springboot.dao.mybatis;

import com.boot.springboot.domain.mybatis.UserDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-31
 * @Version: V1.0
 * @Description: Mybatis持久化
 */
public interface UserDomainMapper{
    UserDomain findByLoginName(@Param("loginName") String loginName);

    List<UserDomain> findAll();
}
