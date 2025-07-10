package com.duanjh.oauth2.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-07-02 周三 17:24
 * @Version: v1.0
 * @Description: SpringSecurity Web控制器
 */
@Controller
public class SecurityAuthController {

    @GetMapping("/home")
    public String index(Model model){
        return "home.html";
    }

    @RequestMapping("/loginSuccess")
    @ResponseBody
    public String loginSuccess(){
        return "loginSuccess";
    }

    @ResponseBody
//    @Secured("ROLE_data:all")   // 表示当前资源需要“ROLE_data:all”权限才能访问，RoleVoter
    @PreAuthorize("hasAuthority('data:all')")
    @RequestMapping("/data/all")
    public String all(){
        return "data.all";
    }

    @ResponseBody
//    @Secured("ROLE_data:add")   // 表示当前资源需要“ROLE_data:add”权限才能访问，RoleVoter
    @PreAuthorize("hasAuthority('data:add')")
    @RequestMapping("/data/add")
    public String add(){
        return "data.add";
    }

    @ResponseBody
    @PreAuthorize("hasAuthority('data:del')")
    @RequestMapping("/data/del")
    public String del(){
        return "data.del";
    }

    @ResponseBody
    @PreAuthorize("hasAnyAuthority('data:add','data:edit')")    //指明了方法必须要有 data:add 或者 data:edit的权限才能访问，ExpressionBasedPreInvocationAdvice
    @RequestMapping("/data/edit")
    public String edit(){
        return "data.edit";
    }

    @ResponseBody
    @RequestMapping("/data/select")
    @PreAuthorize("hasAuthority('data:select')")
    public String select(){
        return "data.select";
    }
}
