package com.kingroad.pulsar.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 11:21
 * @Version: v1.0
 * @Description: 登录页面与主页
 */
@Controller
public class LoginController {

    // 跳转登录页面
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 登录成功首页
    @GetMapping("/index")
    public String index(Authentication authentication, Model model) {
        model.addAttribute("user", authentication.getPrincipal());
        return "index";
    }

}
