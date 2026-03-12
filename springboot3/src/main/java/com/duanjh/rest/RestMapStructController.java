package com.duanjh.rest;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.jpa.repository.BootUserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-12 周四 15:46
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/rest")
public class RestMapStructController {

    @Autowired
    BootUserRepository repository;

    /**
     * 查询所有
     */
    @GetMapping()
    public ResponseEntity<List<BootUserResp>> list() {
        List<BootUser> res = repository.findAll();
        return ResponseEntity.ok(BootUserConvert.INSTANCE.entityToResponse(res));
    }
    /**
     * 获取详情
     */
    @SneakyThrows       // 消去异常处理的模版代码
    @GetMapping(value = "/{id}")
    public ResponseEntity<BootUserResp> detail(@PathVariable("id") Long id) {
        Optional<BootUser> optionalZoo = repository.findById(id);
        if (optionalZoo.isPresent()) {
            return ResponseEntity.ok(BootUserConvert.INSTANCE.entityToResponse(optionalZoo.get()));
        }
        return ResponseEntity.badRequest().build();
    }
    /**
     * 新增
     */
    @PostMapping
    public ResponseEntity<BootUserResp> create(@RequestBody @Validated BootUserReq buq) {
        BootUser u = BootUserConvert.INSTANCE.requestToEntity(buq);
        repository.save(u);
        return ResponseEntity.ok(BootUserConvert.INSTANCE.entityToResponse(u));
    }
    /**
     * 删除
     */
    @SneakyThrows
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        Optional<BootUser> user = repository.findById(id);
        if (user.isPresent()) {
            repository.deleteById(id);
        }
    }
    /**
     * 更新
     */
    @SneakyThrows
    @PutMapping(value = "/{id}")
    public ResponseEntity<BootUserResp> update(@PathVariable("id") Long id, @RequestBody @Validated BootUserReq bsq) {
        if (repository.findById(id).isPresent()) {
            BootUser user = BootUserConvert.INSTANCE.requestToEntity(bsq);
            user.setId(id);
            return  ResponseEntity.ok(BootUserConvert.INSTANCE.entityToResponse(user));
        }
        return ResponseEntity.badRequest().build();
    }
}
