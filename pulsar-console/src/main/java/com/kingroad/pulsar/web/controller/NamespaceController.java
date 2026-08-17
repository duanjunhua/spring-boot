package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.service.PulsarClusterService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-13 周四 15:06
 * @Version: v1.0
 * @Description: Pulsar命名空间管理
 */
@RestController
@RequestMapping("/namespace")
public class NamespaceController {

    @Resource
    PulsarClusterService pulsarClusterService;

    /**
     * 查询默认租户下所有namespace
     * @return
     */
    @GetMapping("/all")
    public Result<Void> allNamespaces(){

        return Result.success();
    }

}
