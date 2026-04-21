package com.duanjh.modulith.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-15 周三 14:47
 * @Version: v1.0
 * @Description:
 */
@Slf4j
@Service
public class NotificationService {

    /*
    public void createNotification(NotificationVo notification) {
        log.info("Received notification by module dependency for product {} in date {} by {}.",
                notification.getProductName(),
                notification.getDate(),
                notification.getFormat());
    }
    */

    /**
     * 应用模块监听
     */
    // @Async  //异步注解，提升性能，若开启需要启动类加上@EnableAsync注解
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener // 确保事件在发布事务提交后才被处理
    public void notify(NotificationVo message) {
        log.info("Received notification by module dependency for product {} in date {} by {}.",
                message.getProductName(),
                message.getDate(),
                message.getFormat());
    }
}