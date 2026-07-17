package com.kingroad.pulsar.audit;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.kingroad.pulsar.auth.bo.LoginUser;
import com.kingroad.pulsar.entity.core.SysAuditLog;
import com.kingroad.pulsar.service.core.SysAuditLogService;
import com.kingroad.pulsar.util.DataCompareUtil;
import com.kingroad.pulsar.util.IpUtil;
import com.kingroad.pulsar.util.PermissionUtil;
import com.kingroad.pulsar.util.SpringContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 10:56
 * @Version: v1.0
 * @Description: 自动记录日志
 */
@Slf4j
@Aspect
@Component
public class AuditLogAspect {

    @Resource
    private SysAuditLogService auditLogService;

    /**
     * 切点：所有标记@AuditLog的方法
     */
    @Pointcut("@annotation(com.kingroad.pulsar.audit.Audit)")
    public void auditLogPointCut() {

    }

    @Around("auditLogPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();

        // 获取请求对象
        RequestAttributes requestAttr = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttr).getRequest();

        // 获取注解信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        Audit auditAnn = signature.getMethod().getAnnotation(Audit.class);

        // 组装审计日志对象
        SysAuditLog auditLog = new SysAuditLog();

        // 当前登录用户
        LoginUser loginUser = PermissionUtil.getLoginUser();
        auditLog.setOperatorId(ObjectUtil.isNull(loginUser) || ObjectUtil.isNull(loginUser.getUser()) ? "匿名用户" : loginUser.getUser().getUserId());
        auditLog.setOperationType(auditAnn.operationType().description());
        auditLog.setTargetResource(request.getRequestURI());
        auditLog.setCreateAt(LocalDateTime.now());
        auditLog.setSourceIp(IpUtil.getIpAddr(request));

        // 请求参数
        Object[] args = point.getArgs();
        String paramJson = JSONUtil.toJsonStr(args);
        auditLog.setParams(StrUtil.sub(paramJson, 0, 2000));

        // 编辑/删除操作：查询数据库原始旧数据
        AuditDto dto = new AuditDto();
        if (auditAnn.saveOldData() && args.length > auditAnn.entityIndex()) {
            Object entityParam = args[auditAnn.entityIndex()];
            // 实体必须包含id字段才能查询旧数据
            try {
                Long id = (Long) entityParam.getClass().getDeclaredMethod("getId").invoke(entityParam);
                if (id != null) {
                    // 反射调用对应mapper/service getById 查询库中原始数据，此处统一扩展：通用方式读取旧实体
                    Object oldEntity = getOldEntityById(point, id);
                    dto.setBeforeChange(DataCompareUtil.toTruncJson(oldEntity, null));
                }
            } catch (Exception e) {
                log.error("读取原始数据失败：{}", e.getMessage());
            }
        }

        // 执行目标接口
        Object result;
        try {
            result = point.proceed();
        } catch (Throwable e) {

            // 异常也保存日志，返回异常信息
            auditLog.setDetails("接口异常：" + e.getMessage());
            long cost = System.currentTimeMillis() - beginTime;
            auditLog.setCreateAt(LocalDateTime.now());
            auditLogService.save(auditLog);
            throw e;
        }

        dto.setAfterChange(DataCompareUtil.toTruncJson(result, null));

        auditLog.setDetails(JSONUtil.toJsonStr(dto));

        // 保存日志
        auditLogService.save(auditLog);
        return result;
    }

    /**
     * 反射根据ID查询数据库原始实体（通用兼容所有业务实体）
     */
    private Object getOldEntityById(ProceedingJoinPoint point, Long id) throws Exception {
        String serviceBeanName = point.getTarget().getClass().getSimpleName()
                .replace("Controller", "ServiceImpl");
        // 从Spring上下文获取service bean，调用getById， 简易通用实现：通过ApplicationContext工具获取bean
        return SpringContextUtil.getBean(serviceBeanName)
                .getClass()
                .getDeclaredMethod("getById", Long.class)
                .invoke(SpringContextUtil.getBean(serviceBeanName), id);
    }
}
