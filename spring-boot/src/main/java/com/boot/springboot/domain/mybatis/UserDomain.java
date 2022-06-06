package com.boot.springboot.domain.mybatis;

import lombok.Data;

import javax.persistence.Column;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-31
 * @Version: V1.0
 * @Description: Mybatis实体类
 */
@Data
public class UserDomain {

    private String id;

    private String name;

    private String loginName;

}
