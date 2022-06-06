package com.boot.springboot.controller;

import com.boot.springboot.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-05-30
 * @Version: V1.0
 * @Description: Freemarker 模板Demo
 */
@Controller
public class FreemarkerDemoController {

    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    static {
        users.put(1L, new User(1L, "Michael J H Duan"));
        users.put(2L, new User(2L, "Hoyce Huang"));
        users.put(3L, new User(3L, "Kay Li"));
    }

    @GetMapping(value = "/api/user/{id}")
    public String findOneUser(Model model, @PathVariable("id") Long id){
        model.addAttribute("user", users.get(id));
        return "user";
    }

    @GetMapping("/api/users")
    public String findAllUser(Model model){
        model.addAttribute("users", users.values());
        return "users";
    }
}
