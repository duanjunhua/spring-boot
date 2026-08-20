package com.duanjh.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-06-16 周二 15:04
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/aop")
public class BusLogController {

    @BusLog(description = "测试自定义注解使用")
    @GetMapping("/access")
    public String accessSelfAnnotation(){
        return "Ok";
    }
}
