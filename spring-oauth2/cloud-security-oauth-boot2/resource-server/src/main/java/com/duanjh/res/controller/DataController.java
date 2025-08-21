package com.duanjh.res.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-20 周三 13:42
 * @Version: v1.0
 * @Description: 资源服务
 */
@RestController
@RequestMapping("/data")
public class DataController {

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('data:all')")    // 如果资源服务器对token校验通过就能够访问该资源
    public String all(){
        return "您的token验证通过,已经访问到真正的资源 data:all";
    }

    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('data:add')")
    public String add(){
        return "您的token验证通过,已经访问到真正的资源 data:add";
    }

    @RequestMapping("/page")
    @PreAuthorize("hasAnyAuthority('data:page')")
    public String page(){
        return "您的token验证通过,已经访问到真正的资源 data:page";
    }
}
