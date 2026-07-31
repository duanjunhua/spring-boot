package com.kingroad.pulsar.service;

import com.kingroad.pulsar.common.PageResult;
import com.kingroad.pulsar.domain.entity.EventDefinition;
import com.kingroad.pulsar.exception.BusinessException;
import com.kingroad.pulsar.repository.EventDefinitionRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 09:05
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class EventDefinitionService {

    @Resource
    EventDefinitionRepository repository;

    /**
     * 分页查询
     */
    public PageResult<EventDefinition> pageAll(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<EventDefinition> page = repository.findAll(pageable);
        return PageResult.of(page.getContent(), page.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 新增修改对象
     */
    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public EventDefinition saveOrUpdate(EventDefinition entity) {
        return repository.save(entity);
    }

    /**
     * 根据ID获取对象
     */
    public EventDefinition findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("查询内容不存在"));
    }

    /**
     * 获取所有
     */
    public List<EventDefinition> findAll(){
        return repository.findAll();
    }
}
