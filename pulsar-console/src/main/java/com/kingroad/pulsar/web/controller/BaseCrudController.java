package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.common.PageQuery;
import com.kingroad.pulsar.common.Result;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:07
 * @Version: v1.0
 * @Description: 通用增删改查
 *      T：实体类
 *      ID：主键类型
 *      <R>： Repository 继承 JpaRepository<T,ID>
 */
public abstract class BaseCrudController<T, ID, R extends JpaRepository<T, ID>> {

    protected final R repository;

    protected BaseCrudController(R repository) {
        this.repository = repository;
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<Page<T>> page(@Valid PageQuery pageQuery) {
        Sort sort = Sort.unsorted();
        if (pageQuery.getSortField() != null && !pageQuery.getSortField().isEmpty()) {
            Sort.Direction direction = "desc".equalsIgnoreCase(pageQuery.getSortOrder())
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            sort = Sort.by(direction, pageQuery.getSortField());
        }
        // JPA分页：page从0开始
        Pageable pageable = PageRequest.of(pageQuery.getPageNum() - 1, pageQuery.getPageSize(), sort);

        Page<T> page = repository.findAll(pageable);
        return Result.success(page);
    }


    /**
     * 根据主键查询
     */
    @GetMapping("/get/{id}")
    public Result<T> getById(@PathVariable ID id) {
        Optional<T> optional = repository.findById(id);
        if (optional.isEmpty()) {
            return Result.error("数据不存在");
        }
        return Result.success(optional.get());
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    public Result<T> save(@Valid @RequestBody T entity) {
        T save = repository.save(entity);
        return Result.success(save);
    }

    /**
     * 修改
     */
    @PostMapping("/edit/{id}")
    public Result<T> update(@PathVariable ID id, @Valid @RequestBody T entity) {
        if (!repository.existsById(id)) {
            return Result.error("数据不存在，无法修改");
        }
        // JPA主键一致即执行更新
        T updated = repository.save(entity);
        return Result.success(updated);
    }

    /**
     * 删除
     */
    @PostMapping("/del/{id}")
    public Result<Void> delete(@PathVariable ID id) {
        if (!repository.existsById(id)) {
            return Result.error("数据不存在");
        }
        repository.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/batch-del")
    public Result<Void> batchDelete(@RequestBody Iterable<ID> idList) {
        repository.deleteAllById(idList);
        return Result.success();
    }
}
