package com.kingroad.pulsar.web.controller;

import com.kingroad.pulsar.domain.entity.AuditLog;
import com.kingroad.pulsar.repository.AuditLogRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-30 周四 10:01
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/audit-log")
public class AuditLogController extends BaseCrudController<AuditLog, Long, AuditLogRepository> {

    public AuditLogController(AuditLogRepository repository) {
        super(repository);
    }

}
