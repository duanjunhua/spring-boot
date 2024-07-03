package com.duanjh.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[Junhua Duan]
 * @Date: 2023-05-16 11:11
 * @Version: V1.0
 * @Description: 测试接口
 */
@RestController
public class HelloController {


    /**
     * 无权限拦截，认证成功都可以访问
     */
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String admin(){
        return "admin";
    }

}

