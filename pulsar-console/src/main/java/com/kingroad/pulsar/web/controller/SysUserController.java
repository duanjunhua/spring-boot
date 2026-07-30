package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.repository.SysUserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:27
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseCrudController<SysUser, Long, SysUserRepository> {

    public SysUserController(SysUserRepository repository) {
        super(repository);
    }

}
