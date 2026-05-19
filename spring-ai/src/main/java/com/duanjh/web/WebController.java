package com.duanjh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-17 周二 14:37
 * @Version: v1.0
 * @Description:
 */
@Controller
public class WebController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
