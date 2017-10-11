package com.springboot.demo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.demo.beans.User;
//使用注解不继承JpaRepository接口
//@RepositoryDefinition(domainClass=User.class, idClass=Integer.class)
public interface UserDao extends JpaRepository<User, Integer>{

	@Query("select u from User u")
	List<User> findAllUser();
	
	@Query("select u from User u where u.id=:id")
	User findUserById(@Param("id") int id);
	
	@Query(value="select * from user where user_name=:userName",nativeQuery=true)
	User findUserByUserName(@Param("userName") String userName);
	
	@Modifying
	@Transactional
	@Query(value="update user set user_name=:userName where id=:id",nativeQuery=true)
	void updateUserById(@Param("id") int id, @Param("userName") String userName);
	
	@Query(value="select sub_id as id, name as user_name, pass as password, '' as adress, '' as email, '' as description from subUser where sub_id=:subId",nativeQuery=true)
	User findUserBySubId(@Param("subId") int subId);
}
