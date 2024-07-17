package com.duanjh.module.interface_interact;

import com.duanjh.module.interface_interact.aop.ResResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Michale J H Duan[JunHua]
 * @Date: 2024-07-16 星期二 15:38
 * @Version: v1.0
 * @Description: 统一接口交互
 */
@Slf4j
@ResResult  //自定义返回注解
@Controller
@RequestMapping("/interface_interact")
public class InterfaceInteractController {

    @GetMapping("/custom-res-anno/{userName}/{age}")
    @ResponseBody
    public Object customResAnno(@PathVariable("userName") String userName, @PathVariable("age") Integer age) {
        Map<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("age", age);
        return result;
    }
}
