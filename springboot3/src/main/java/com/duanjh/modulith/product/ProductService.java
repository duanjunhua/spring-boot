package com.duanjh.modulith.product;

import com.duanjh.modulith.notification.NotificationVo;
import com.duanjh.modulith.notification.NotificationService;
import com.duanjh.modulith.product.internal.Product;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-15 周三 14:47
 * @Version: v1.0
 * @Description:
 */
@Service
public class ProductService {

    ApplicationEventPublisher events;

    private final NotificationService notificationService;

    public ProductService(ApplicationEventPublisher events, NotificationService notificationService) {
        this.events = events;
        this.notificationService = notificationService;
    }

    /*
    public void create(Product product) {
        // 调用了notification模块暴露的NotificationService API，并创建Notification实例
        notificationService.createNotification(new NotificationVo(new Date(), "SMS", product.getName()));
    }
    */

    /**
     * 发布领域事件
     */
    public void publish(Product product) {
        events.publishEvent(new NotificationVo(new Date(), "SMS", product.getName()));
    }
}