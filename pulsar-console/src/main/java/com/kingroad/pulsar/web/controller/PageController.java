package com.kingroad.pulsar.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-31 周五 16:10
 * @Version: v1.0
 * @Description: 页面跳转
 */
@Controller
public class PageController {

    // 登录成功首页
    @GetMapping("/cluster")
    public String cluster() {
        return "cluster/cluster-list";
    }

    // 登录成功首页
    @GetMapping("/tenant")
    public String tenant() {
        return "tenant/tenant-list";
    }

}
