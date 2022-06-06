package com.boot.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-02
 * @Version: V1.0
 * @Description: 面向切面
 */
@RestController
public class AopController {

    @GetMapping("/aop/{name}")
    public String helloAop(@PathVariable("name")String name){
        return "Hello, Welcome Aop: " + name;
    }
}
