package com.duanjh.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-05 周四 13:46
 * @Version: v1.0
 * @Description: 自定义事件
 */
@Getter
public class PersonalEvent extends ApplicationEvent {

    private String address;

    private String content;

    public PersonalEvent(Object source, String address, String content) {
        super(source);
        this.address = address;
        this.content = content;
    }

}
