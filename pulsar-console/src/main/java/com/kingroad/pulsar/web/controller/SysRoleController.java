package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.domain.entity.SysRole;
import com.kingroad.pulsar.repository.SysRoleRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:26
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/role")
public class SysRoleController extends BaseCrudController<SysRole, Long, SysRoleRepository> {

    public SysRoleController(SysRoleRepository repository) {
        super(repository);
    }

}
