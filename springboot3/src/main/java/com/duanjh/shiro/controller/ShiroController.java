package com.duanjh.shiro.controller;

import com.duanjh.shiro.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-12 周四 11:07
 * @Version: v1.0
 * @Description: 登录实现
 */
@Slf4j
@Controller
public class ShiroController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/unauthorized")
    public String forbidden(){
        return "unauthorized";
    }

    @RequestMapping("/loginPage")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute UserVo userVo) {
        try {
            // 获取主体对象
            Subject subject = SecurityUtils.getSubject();

            subject.login(new UsernamePasswordToken(userVo.getUsername(), userVo.getPassword()));

            log.info("登录成功：{}", subject.isAuthenticated());

            return "redirect:/index";

        }catch (UnknownAccountException e){
            log.error("当前用户不存在");
        }catch (IncorrectCredentialsException e){
            log.error("账号或密码错误");
        }catch (Exception e){
            log.error("服务器错误");
        }
        return "redirect:/loginPage";
    }

    @RequiresPermissions("userInfo:view")       // 表示需要userInfo:view权限
    @RequestMapping("/sysUser/list")
    public String list(){
        return "userInfo";
    }

    @RequiresPermissions("userInfo:add")       // 表示需要userInfo:add权限
    @RequestMapping("/sysUser/add")
    public String add(){
        return "userInfoAdd";
    }
}
