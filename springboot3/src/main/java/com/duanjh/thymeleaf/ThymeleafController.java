package com.duanjh.thymeleaf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-06 周五 14:54
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    @GetMapping("/index")
    public String index(){
        return "common";
    }

}
