package com.kingroad.pulsar.authorization.service;

import com.kingroad.pulsar.authorization.covert.SysUserConvert;
import com.kingroad.pulsar.authorization.sso.SsoConst;
import com.kingroad.pulsar.common.CommonConst;
import com.kingroad.pulsar.config.RsaConfig;
import com.kingroad.pulsar.domain.dto.SystemInitDto;
import com.kingroad.pulsar.domain.entity.GlobalConfig;
import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.service.GlobalConfigService;
import com.kingroad.pulsar.service.SysUserService;
import com.kingroad.pulsar.util.EncryptUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-04 周二 10:54
 * @Version: v1.0
 * @Description: 系统初始化
 */
@Slf4j
@Service
public class InitService {

    @Resource
    GlobalConfigService configService;

    @Resource
    SysUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /** 查询系统是否已初始化 */
    public boolean isInited(){
        String initFinished = configService.findValByConfigKey(CommonConst.INIT_SYSTEM); //initConfigRepository.findFirstByOrderByIdAsc().orElseThrow();
        return StringUtils.isNotBlank(initFinished);
    }

    /** 执行系统初始化 */
    @Transactional(rollbackFor = Exception.class)
    public void doInit(SystemInitDto dto) throws Exception {

        // 1.校验两次密码, 该前端校验
//        if(!dto.getPasswordHash().equals(dto.getConfirmPassword())){
//            throw new BusinessException("两次输入密码不一致");
//        }

        /**
         * 前端传输密码解码并进行SHA加密存储
         */
        String originiPwd = EncryptUtil.decryptWithRsa(dto.getPasswordHash(), EncryptUtil.getRsaPrivateKey(RsaConfig.PRIVATE_KEY));

        dto.setPasswordHash(passwordEncoder.encode(originiPwd));

        // 2.创建超级管理员
        SysUser adminUser =  SysUserConvert.INSTANCE.requestToEntity(dto);
        userService.saveOrUpdate(adminUser);

        // 3.更新初始化配置与SSO参数
        List<GlobalConfig> configs = new ArrayList<>();
        if(dto.getSsoEnable()){
            configs.add(buildGlobalConfig(SsoConst.SSO_ENABLE, SsoConst.ACTIVE));
        }

        if(StringUtils.isNoneBlank(dto.getRegistrationId())){
            configs.add(buildGlobalConfig(SsoConst.REGISTRATION_ID, dto.getRegistrationId()));
        }

        if(StringUtils.isNoneBlank(dto.getClientId())){
            configs.add(buildGlobalConfig(SsoConst.CLIENT_ID, dto.getClientId()));
        }

        if(StringUtils.isNoneBlank(dto.getClientSecret())){
            configs.add(buildGlobalConfig(SsoConst.CLIENT_SECRET, dto.getClientSecret()));
        }

        if(StringUtils.isNoneBlank(dto.getAuthorizationUri())){
            configs.add(buildGlobalConfig(SsoConst.AUTHORIZATION_URI, dto.getAuthorizationUri()));
        }

        if(StringUtils.isNoneBlank(dto.getTokenUri())){
            configs.add(buildGlobalConfig(SsoConst.TOKEN_URI, dto.getTokenUri()));
        }

        if(StringUtils.isNoneBlank(dto.getRedirectUri())){
            configs.add(buildGlobalConfig(SsoConst.REDIRECT_URI, dto.getRedirectUri()));
        }

        if(StringUtils.isNoneBlank(dto.getJwtSetUri())){
            configs.add(buildGlobalConfig(SsoConst.JWT_SET_URI, dto.getJwtSetUri()));
        }

        // 4. 初始化完成配置
        configs.add(buildGlobalConfig(CommonConst.INIT_SYSTEM,  CommonConst.COM_ONE));

        configService.saveAll(configs);
    }

    private GlobalConfig buildGlobalConfig(String configKey, String configValue){
        GlobalConfig config = new GlobalConfig();
        config.setConfigKey(configKey);
        config.setConfigValue(configValue);
        return config;
    }
}
