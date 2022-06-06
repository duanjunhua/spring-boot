package com.boot.springboot.controller;

import cn.hutool.core.bean.BeanUtil;
import com.boot.springboot.domain.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-31
 * @Version: V1.0
 * @Description: Redis连接
 */
@Controller
public class RedisController {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/redis")
    public String redis(Model model){
        User user = new User(1L,"Michael J H Duan");
        redisTemplate.opsForValue().set(user.getId().toString(), user);
        model.addAttribute("redis", redisTemplate.opsForValue().get(user.getId().toString()));
        return "redis";
    }
}
