package com.boot.springboot.controller;

import com.boot.springboot.dao.mybatis.OrgDomainMapper;
import com.boot.springboot.dao.mybatis.UserDomainMapper;
import com.boot.springboot.domain.mybatis.OrgDomain;
import com.boot.springboot.domain.mybatis.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-31
 * @Version: V1.0
 * @Description: Mybatis Demo
 */
@RestController
public class MybatisController {

    @Autowired
    UserDomainMapper userDomainDao;

    @Autowired
    OrgDomainMapper orgDomainMapper;

    @GetMapping("/mybatis/{loginName}")
    public UserDomain findByUserLoginName(@PathVariable("loginName") String loginName){
        return userDomainDao.findByLoginName(loginName);
    }

    @GetMapping("/mybatis")
    public List<UserDomain> findAll(){
        return userDomainDao.findAll();
    }


    @GetMapping("/mybatis/org/{name}")
    public OrgDomain findByOrgName(@PathVariable("name") String name){
        return orgDomainMapper.findByName(name);
    }

    @GetMapping("/mybatis/org")
    public List<OrgDomain> findAllOrg(){
        List<OrgDomain> all = orgDomainMapper.findAll();
        return all;
    }
}
