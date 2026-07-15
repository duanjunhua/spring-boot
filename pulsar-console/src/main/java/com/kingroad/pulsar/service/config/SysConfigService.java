package com.kingroad.pulsar.service.config;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kingroad.pulsar.entity.config.SysConfig;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:18
 * @Version: v1.0
 * @Description:
 */
public interface SysConfigService extends IService<SysConfig> {

    String getConfigValue(String key);

    void saveConfig(String key, String value);

}
