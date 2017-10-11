package com.springboot.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.springboot.demo.beans.User;

public interface UserMapper {

	@Select("select * from user")
	List<User> getAll();
	
	@Select("select * from user where id=#{id}")
	User getUserById(@Param("id") int id);
}
