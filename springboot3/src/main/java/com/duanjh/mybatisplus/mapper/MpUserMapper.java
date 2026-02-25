package com.duanjh.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duanjh.mybatisplus.entity.MpUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-06 周五 15:57
 * @Version: v1.0
 * @Description:
 */
@Mapper
public interface MpUserMapper extends BaseMapper<MpUser> {

    MpUser getMpUserByEmail(@Param("email") String email);

    // 不使用xml
    @Select("select * from t_boot_user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email")
    })
    List<MpUser> getList();

    // 不使用xml
    @Select("select * from t_boot_user where username = #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email")
    })
    MpUser findMpUserByUsername(@Param("username") String username);

    @Insert("insert into t_boot_user(username, password, email) values(#{username}, #{password}, #{email})")
    int insert(MpUser user);
}
