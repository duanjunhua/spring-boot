package com.kingroad.pulsar.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kingroad.pulsar.auth.bo.LoginUser;
import com.kingroad.pulsar.entity.uo.SysUser;
import com.kingroad.pulsar.util.PermissionUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-15 周三 10:23
 * @Version: v1.0
 * @Description: 自动注入时间
 *      也可通过注解在字段上标注：@TableField(fill = FieldFill.INSERT)，并开启自动填充策略：
 *          mybatis-plus.global-config.db-config.auto-fill-strategy=true    # 默认是false
 */
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {
    /**
     * 创建
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("createAt", LocalDateTime.now(), metaObject);

        SysUser loginUser = ObjectUtil.isNull(PermissionUtil.getLoginUser()) ? null : PermissionUtil.getLoginUser().getUser();
        if(ObjectUtil.isNotNull(loginUser)){
            this.setFieldValByName("creatorUserId", loginUser.getId(), metaObject);
        }
    }

    /**
     * 更新
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updateAt", LocalDateTime.now(), metaObject);

        // 有版本号则版本号递增
        if(metaObject.hasGetter("versionNumber")){
            Integer versionNumber = (Integer)this.getFieldValByName("versionNumber", metaObject);
            this.setFieldValByName("versionNumber", versionNumber.intValue() + 1, metaObject);
        }

        SysUser loginUser = ObjectUtil.isNull(PermissionUtil.getLoginUser()) ? null : PermissionUtil.getLoginUser().getUser();
        if(ObjectUtil.isNotNull(loginUser)){
            this.setFieldValByName("changedBy", loginUser.getId(), metaObject);
        }
    }
}
