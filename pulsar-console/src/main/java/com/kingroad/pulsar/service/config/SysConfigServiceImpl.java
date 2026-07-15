package com.kingroad.pulsar.service.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingroad.pulsar.entity.config.SysConfig;
import com.kingroad.pulsar.mapper.SysConfigMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-14 周二 10:18
 * @Version: v1.0
 * @Description:
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Resource
    private SysConfigMapper configMapper;

    @Override
    public String getConfigValue(String key) {

        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigKey, key);

        SysConfig config = getOne(wrapper);

        return ObjectUtil.isNull(config) ? "" : config.getConfigValue();
    }

    @Override
    public void saveConfig(String key, String value) {

        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigKey, key);

        SysConfig exist = getOne(wrapper);

        if(ObjectUtil.isNull(exist)) {
            SysConfig c = new SysConfig();
            c.setConfigKey(key);
            c.setConfigValue(value);
            save(c);
            return;
        }

        exist.setConfigValue(value);
        updateById(exist);
    }
}
