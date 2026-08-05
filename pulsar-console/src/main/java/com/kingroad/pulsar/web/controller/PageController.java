package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.authorization.service.InitService;
import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.domain.dto.SystemInitDto;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-31 周五 16:10
 * @Version: v1.0
 * @Description: 页面跳转
 */
@Controller
public class PageController {

    @Resource
    InitService initService;

    /**
     * 初始化引导界面
     */
    @GetMapping("/system/init")
    public String init(Model model) {
        if(initService.isInited()) {
            return "redirect:/login";
        }
        model.addAttribute("initForm", new SystemInitDto());
        return "init";
    }

    /**
     * 提交初始化配置
     */
    @ResponseBody
    @PostMapping("/init/save")
    public Result<Object> submitInit(@Valid SystemInitDto dto, RedirectAttributes redirectAttributes) {
        if(initService.isInited()){
            redirectAttributes.addFlashAttribute("msg","系统已初始化！");
            return Result.success("系统已初始化!");
        }
        try {
            initService.doInit(dto);
            redirectAttributes.addFlashAttribute("success","初始化完成，请登录超级管理员账号！");
            return Result.success();
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            redirectAttributes.addFlashAttribute("formData",dto);
            return Result.error(e.getMessage());
        }
    }

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
