package com.boot.springboot.dao.mybatis;

import com.boot.springboot.domain.mybatis.OrgDomain;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-01
 * @Version: V1.0
 * @Description: 机构持久化
 */
@Mapper
public interface OrgDomainMapper {

    @Select("select id, name, level_code from org_org where name = ${name}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "levelCode", column = "level_code")
    })
    OrgDomain findByName(@Param("name") String name);

    @Select("select id, name, level_code from org_org")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "levelCode", column = "level_code")
    })
    List<OrgDomain> findAll();
}
