package com.duanjh;

import com.duanjh.mail.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 09:42
 * @Version: v1.0
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTests {

    @Autowired
    private MailService mailService;

    @Test
    public void simpleMessageTest() {
        mailService.sendSimpleMail("2550564538@qq.com", "1585115894@qq.com", "测试邮件", "这是一条测试邮件内容");
    }

}
