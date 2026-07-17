package com.duanjh.oauth.controller;

import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

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

    @Resource
    private AuthorizationServerSettings authServerSettings;

    // 登录成功跳转首页
    @GetMapping("/index")
    public String index(Authentication authentication, Model model) {
        // 当前登录用户名
        String username = authentication.getName();
        // 用户权限集合
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        // 签发者地址
        String issuer = authServerSettings.getIssuer();

        model.addAttribute("username", username);
        model.addAttribute("authorities", authorities);
        model.addAttribute("issuer", issuer);
        return "index";
    }
}
