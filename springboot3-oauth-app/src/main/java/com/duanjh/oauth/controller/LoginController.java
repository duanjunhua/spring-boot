package com.duanjh.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 15:58
 * @Version: v1.0
 * @Description: 登录页面控制器
 */
@Controller
public class LoginController {

    // 自定义登录页面，可替换Thymeleaf模板
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
