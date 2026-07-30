package com.kingroad.pulsar.service;

import com.kingroad.pulsar.aop.Log;
import com.kingroad.pulsar.common.PageResult;
import com.kingroad.pulsar.domain.entity.GlobalConfig;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.repository.GlobalConfigRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 09:07
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class GlobalConfigService {

    @Resource
    GlobalConfigRepository repository;

    /**
     * 分页查询
     */
    @Log(operation = Log.OperationType.PAGE, description = "分页查询全局配置")
    public PageResult<GlobalConfig> pageAll(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<GlobalConfig> page = repository.findAll(pageable);
        return PageResult.of(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 新增修改对象
     */
    public GlobalConfig saveOrUpdate(GlobalConfig entity) {
        return repository.save(entity);
    }

    /**
     * 根据ID获取对象
     */
    public GlobalConfig findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("查询内容不存在"));
    }

    /**
     * 获取所有
     */
    public List<GlobalConfig> findAll(){
        return repository.findAll();
    }

    /**
     * 根据对象查询
     */
    public List<GlobalConfig> getUniqueGlobalConfig(GlobalConfig entity) {

        if(ObjectUtils.isEmpty(entity)) return null;

        Example<GlobalConfig> condition = Example.of(entity);

        return repository.findAll(condition);
    }

    /**
     * 根据属性查找
     */
    public GlobalConfig findEntityByConfigKey(String  configKey) {
        return repository.findByConfigKey(configKey);
    }

    /**
     * 根据属性查找
     */
    public String findValByConfigKey(String  configKey) {

        GlobalConfig config = repository.findByConfigKey(configKey);

        if(ObjectUtils.isEmpty(config)) return StringUtils.EMPTY;

        return config.getConfigValue();
    }

}
