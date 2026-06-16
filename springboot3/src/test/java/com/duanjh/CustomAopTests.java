package com.duanjh;

import com.duanjh.aop.BusLogController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-06-16 周二 15:07
 * @Version: v1.0
 * @Description: 自定义注解测试
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomAopTests {

    @Autowired
    BusLogController  busLogController;

    @Test
    public void testAop(){
        String s = busLogController.accessSelfAnnotation();
        Assert.assertEquals("Ok", s);
    }
}
