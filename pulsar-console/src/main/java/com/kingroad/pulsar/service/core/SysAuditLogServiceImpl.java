package com.kingroad.pulsar.service.core;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kingroad.pulsar.entity.core.SysAuditLog;
import com.kingroad.pulsar.mapper.SysAuditLogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 10:59
 * @Version: v1.0
 * @Description:
 */
@Service
public class SysAuditLogServiceImpl extends ServiceImpl<SysAuditLogMapper, SysAuditLog> implements SysAuditLogService {

    @Resource
    private SysAuditLogMapper sysAuditLogMapper;

}
