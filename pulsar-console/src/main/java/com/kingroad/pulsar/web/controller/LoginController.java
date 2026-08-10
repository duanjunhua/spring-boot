package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.authorization.sso.SsoConst;
import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.config.RsaConfig;
import com.kingroad.pulsar.service.GlobalConfigService;
import com.kingroad.pulsar.util.EncryptUtil;
import com.kingroad.pulsar.util.SecurityUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyPair;
import java.util.Base64;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 11:21
 * @Version: v1.0
 * @Description: 登录页面与主页
 */
@Controller
public class LoginController {

    @Resource
    GlobalConfigService configService;

    // 跳转登录页面
    @GetMapping("/login")
    public String loginPage(Model model) {

        String ssoEnable = configService.findValByConfigKey(SsoConst.SSO_ENABLE);
        model.addAttribute("ssoEnable", StringUtils.equals(ssoEnable, SsoConst.ACTIVE));

        String ssoRigistrationId = configService.findValByConfigKey(SsoConst.REGISTRATION_ID);
        model.addAttribute("registrationId", ssoRigistrationId);
        return "login";
    }

    // 登录成功首页
    @GetMapping("/index")
    public String index(Authentication authentication, Model model) {
        model.addAttribute("user", SecurityUtil.getLoginUser());
        return "index";
    }

    /**
     * 用于供前端获取公钥加密登录密码
     * @return
     */
    @GetMapping("/rsa/get-pubkey")
    @ResponseBody
    public Result<?> getPublicKey() {
        return Result.success(EncryptUtil.wrapPublicKey(RsaConfig.PUBLIC_KEY));
    }

    /**
     * 用于密钥泄露后重新生成密钥
     * @return 新的公钥
     */
    @GetMapping("/generate-key-pair")
    @ResponseBody
    public Result<?> generateKeyPair() {
        KeyPair keyPair = EncryptUtil.getRsaKeyPair();
        RsaConfig.PUBLIC_KEY = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        RsaConfig.PRIVATE_KEY = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        return Result.success(EncryptUtil.wrapPublicKey(RsaConfig.PUBLIC_KEY));
    }
}
