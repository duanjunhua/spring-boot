package com.kingroad.pulsar.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:49
 * @Version: v1.0
 * @Description: 登录 + 进入主页
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, Authentication auth) {
        model.addAttribute("loginUser", auth.getName());
        return "welcome";
    }
}
