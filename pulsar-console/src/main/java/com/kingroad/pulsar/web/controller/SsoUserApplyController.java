package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.aop.Log;
import com.kingroad.pulsar.common.Result;
import com.kingroad.pulsar.domain.dto.SsoUserApplyDto;
import com.kingroad.pulsar.domain.entity.SsoUserApply;
import com.kingroad.pulsar.domain.entity.SysUser;
import com.kingroad.pulsar.mapstruct.SsoUserApplyConvert;
import com.kingroad.pulsar.repository.SsoUserApplyRepository;
import com.kingroad.pulsar.service.SsoUserApplyService;
import com.kingroad.pulsar.service.SysUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-08-10 周一 14:38
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/sso/user/apply")
public class SsoUserApplyController extends BaseCrudController<SsoUserApply, Long, SsoUserApplyRepository>{

    public SsoUserApplyController(SsoUserApplyRepository repository) {
        super(repository);
    }

    @Resource
    SsoUserApplyService service;

    @Resource
    SysUserService userService;

    @PostMapping("/submit")
    public Result<SsoUserApply> approve(@RequestBody SsoUserApplyDto dto) {
        if(ObjectUtils.isEmpty(dto) || ObjectUtils.isEmpty(dto.getUserId())){
            return Result.error("用户不能为空");
        }
        SysUser user = userService.findEntityById(dto.getUserId());
        if(ObjectUtils.isEmpty(user)){
            return Result.error("用户不存在");
        }
        SsoUserApply exist = service.findApprovedApplyBySsoId(user.getSsoId());
        if(ObjectUtils.isNotEmpty(exist)){
            return Result.error("用户申请已存在，请等管理员审批后再登录！");
        }

        SsoUserApply entity = new SsoUserApply();

        entity.setUserId(user.getId());
        entity.setSsoId(user.getSsoId());
        entity.setApplyTime(LocalDateTime.now());
        entity.setApplyReason(dto.getApplyReason());
        entity.setStatus(SsoUserApply.ApplyStatus.APPLYING.name());

        repository.save(entity);

        return Result.success(entity);
    }


    @Log(operation = Log.OperationType.UNKNOWN, description = "用户申请审批")
    @PostMapping("/audit")
    public Result<SsoUserApply> audit(@RequestBody SsoUserApplyDto dto) {

        if(ObjectUtils.isEmpty(dto)){
            return Result.error("审批对象不能为空！");
        }

        SsoUserApply entity = repository.findById(dto.getId()).orElse(null);

        if(ObjectUtils.isEmpty(entity)){
            return Result.error("审批对象不存在！");
        }

        entity.setStatus(dto.getStatus());
        entity.setApprovalOpinion(dto.getOpinion());

        entity = service.auditSsoUserApply(entity);

        return Result.success(entity);
    }
}
