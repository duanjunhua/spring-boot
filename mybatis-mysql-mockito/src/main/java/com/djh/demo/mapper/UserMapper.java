package com.djh.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.djh.demo.beans.User;

@Mapper
public interface UserMapper {

	@Select("select id, user_name as userName, password, email, description from user")
	public List<User> findAllUser();

	@Select("select id, user_name as userName, password, email, description from user where id=#{id}")
	public User getUserById(int id);

	@Insert("insert into user(user_name, password, email, description) values(#{user.userName, jdbcType=VARCHAR}, #{user.password}, #{user.email}, #{user.description})")
	public void saveUser(@Param("user") User user);

	@Select("select count(*) from user where email=#{email}")
	public Boolean emailAlreadyExist(@Param("email") String email);

	@Select("select id from user where user_name=#{userName}")
	public Boolean userAlreadyExist(@Param("userName") String userName);

	@Update("update user set password=#{user.password, jdbcType=VARCHAR}, email=#{user.email, jdbcType=VARCHAR}, description=#{user.description, jdbcType=VARCHAR} where user_name=#{user.userName}")
	public void updateUserInfo(@Param("user") User user);
}
