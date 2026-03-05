package com.duanjh.event;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-05 周四 14:10
 * @Version: v1.0
 * @Description: 基于注解的事件监听器
 */
@Slf4j
@Data
@Component
public class BaseAnnotationEventListener {

    private String notificationAddress;

    /**
     * 异步事件监听器方法不能通过返回值来发布后续事件，多个监听器可以添加@Order(num)注解来设置监听器顺序
     */
    @Async  // 开启异步监听
    @EventListener
    public void onApplicationEvent(PersonalEvent event) {
        log.info("---------------【BaseAnnotationEventListener】监听-------");
    }
}
