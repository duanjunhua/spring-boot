package com.duanjh.event;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-05 周四 13:55
 * @Version: v1.0
 * @Description: 事件监听
 */
@Slf4j
@Data
@Component
public class BaseApplicationEventListener implements ApplicationListener<PersonalEvent> {

    private String notificationAddress;

    @Override
    public void onApplicationEvent(PersonalEvent event) {
        log.info("---------------ApplicationListener监听-------");
    }
}
