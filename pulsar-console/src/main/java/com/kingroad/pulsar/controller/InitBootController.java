package com.kingroad.pulsar.controller;

import cn.hutool.core.util.StrUtil;
import com.kingroad.pulsar.audit.Audit;
import com.kingroad.pulsar.auth.dto.OauthInitDTO;
import com.kingroad.pulsar.constant.CommonConst;
import com.kingroad.pulsar.constant.OperateType;
import com.kingroad.pulsar.entity.uo.SysUser;
import com.kingroad.pulsar.res.Result;
import com.kingroad.pulsar.service.config.SysConfigService;
import com.kingroad.pulsar.service.uo.SysUserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 14:45
 * @Version: v1.0
 * @Description:
 */
@Controller
@RequestMapping("/init")
public class InitBootController {

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private PasswordEncoder encoder;

    @GetMapping("/guide")
    public String guidePage() {
        return "init-guide";
    }

    @Audit(module = "系统超级用户初始化", operationType = OperateType.CREATE)
    @PostMapping("/save")
    public String saveInit(@Valid OauthInitDTO dto) {

        initBootUser(dto);

        return "login";
    }

    @Audit(module = "系统超级用户初始化", operationType = OperateType.CREATE)
    @PostMapping("/save-json")
    @ResponseBody
    public Result<?> saveInitWithJson(@Valid OauthInitDTO dto) {

        initBootUser(dto);

        return Result.success("系统初始化完成，请前往登录页", "");
    }

    private void initBootUser(OauthInitDTO dto) {
        // 创建超级管理员
        SysUser admin = new SysUser();
        admin.setUsername("pulsar");
        admin.setTenantName("zevent");
        admin.setUserId("admin");
        admin.setPulsarClusterId(1);
        admin.setPasswordHash(encoder.encode(dto.getPasswordHash()));
        admin.setIsSuperAdmin(Boolean.TRUE);
        admin.setEnable(Boolean.TRUE);
        sysUserService.save(admin);

        // 保存OAuth配置
        sysConfigService.saveConfig(CommonConst.SSO_ENABLE, dto.getOauthEnable().toString());

        if(dto.getOauthEnable()) {
            sysConfigService.saveConfig("oauth_client_id", StrUtil.blankToDefault(dto.getClientId(),""));
            sysConfigService.saveConfig("oauth_client_secret", StrUtil.blankToDefault(dto.getClientSecret(),""));
            sysConfigService.saveConfig("oauth_authorization_uri", StrUtil.blankToDefault(dto.getAuthUri(),""));
            sysConfigService.saveConfig("oauth_token_uri", StrUtil.blankToDefault(dto.getTokenUri(),""));
            sysConfigService.saveConfig("oauth_userinfo_uri", StrUtil.blankToDefault(dto.getUserInfoUri(),""));
            sysConfigService.saveConfig("oauth_redirect_uri", StrUtil.blankToDefault(dto.getRedirectUri(),""));
        }

        // 标记初始化完成
        sysConfigService.saveConfig("init_admin_flag", CommonConst.FLAG_ONE);
    }
}
