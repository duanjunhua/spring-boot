package com.duanjh.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-03-11 周二 11:04
 * @Version: v1.0
 * @Description: 服务首页
 */
@Controller
public class IndexController {

    @Value("${cas.server-url-prefix}")
    private String casServerUri;

    @Value("${cas.server-logout-url}")
    private String logoutUrl;

    @RequestMapping("/service-b")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping("/logout")
    public String logout(){
        return "redirect:" + logoutUrl;
    }

}
