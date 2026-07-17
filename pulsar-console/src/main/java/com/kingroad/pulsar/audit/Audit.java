package com.kingroad.pulsar.audit;

import com.kingroad.pulsar.constant.OperateType;

import java.lang.annotation.*;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-16 周四 10:54
 * @Version: v1.0
 * @Description: 自定义审计日志注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Audit {

    /**
     * 操作模块名称
     */
    String module() default "";

    /**
     * 操作类型
     */
    OperateType operationType() default OperateType.UNKNOW;

    /**
     * 操作描述
     */
    String description() default "";

    /**
     * 是否需要查询旧数据（编辑/删除=true，新增=false）
     */
    boolean saveOldData() default false;

    /**
     * 方法参数下标：实体对象位置，用于自动对比新旧数据
     */
    int entityIndex() default 0;
}
