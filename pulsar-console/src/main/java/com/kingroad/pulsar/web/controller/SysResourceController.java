package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.domain.entity.SysResource;
import com.kingroad.pulsar.repository.SysResourceRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:25
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/res")
public class SysResourceController extends BaseCrudController<SysResource, Long, SysResourceRepository> {

    public SysResourceController(SysResourceRepository repository) {
        super(repository);
    }

}
