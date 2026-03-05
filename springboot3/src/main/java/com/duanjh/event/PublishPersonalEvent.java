package com.duanjh.event;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-05 周四 13:49
 * @Version: v1.0
 * @Description: 发布事件
 *  要发布一个自定义的ApplicationEvent，在ApplicationEventPublisher上调用publishEvent()方法。通过创建一个实现ApplicationEventPublisherAware的类并将其注册为Spring bean来完成
 */
@Slf4j
@Service
public class PublishPersonalEvent implements ApplicationEventPublisherAware {

    private List<String> personalEvents;

    private ApplicationEventPublisher publisher;

    public void setPersonalEvents(List<String> personalEvents) {
        this.personalEvents = personalEvents;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void sendPersonalEvent(String address, String content){
        log.info("-----------发布事件-------------");
        publisher.publishEvent(new PersonalEvent(this, address, content));
    }
}
