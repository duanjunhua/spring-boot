package com.boot.vue.controller;

import com.boot.vue.entity.Vue;
import com.boot.vue.request.VueREQ;
import com.boot.vue.result.Result;
import com.boot.vue.service.IVueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-07
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/vue")
public class VueController {

    @Autowired
    private IVueService vueService;

    @GetMapping("/")
    public Result hello(){
        return Result.ok();
    }

   @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page, @PathVariable("size") long size, @RequestBody(required = false)VueREQ req){
        log.info("分页查询列表：page={}, size={}", page, size);
        return vueService.search(page, size, req);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Vue vue){
        return vueService.save(vue) ?Result.ok() : Result.error("新增信息失败！");
    }

    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable("id") Integer id){
        return vueService.removeById(id) ? Result.ok() : Result.error("删除信息失败");
    }

}
