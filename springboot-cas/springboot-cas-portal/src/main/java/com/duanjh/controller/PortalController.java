package com.duanjh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-03-11 周二 11:03
 * @Version: v1.0
 * @Description: 门户服务
 */
@Controller
public class PortalController {


    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
