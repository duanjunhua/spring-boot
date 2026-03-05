package com.duanjh;

import com.duanjh.event.PublishPersonalEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-05 周四 14:01
 * @Version: v1.0
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationEventTests {

    @Autowired
    PublishPersonalEvent publish;

    @Test
    public void publishEventTest(){
        publish.setPersonalEvents(Arrays.asList("first@qq.com", "second@qq.com", "third@qq.com"));
        publish.sendPersonalEvent("first@qq.com", "发布的内容");
    }
}
