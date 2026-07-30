package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.domain.entity.GlobalConfig;
import com.kingroad.pulsar.repository.GlobalConfigRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:23
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/global-conf")
public class GlobalConfigController extends BaseCrudController<GlobalConfig, Long, GlobalConfigRepository> {

    public GlobalConfigController(GlobalConfigRepository repository) {
        super(repository);
    }

}
