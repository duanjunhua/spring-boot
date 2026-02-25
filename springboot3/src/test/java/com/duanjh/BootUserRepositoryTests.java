package com.duanjh;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.jpa.repository.BootUserReposotory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-05 周四 16:41
 * @Version: v1.0
 * @Description: 持久类测试
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BootUserRepositoryTests {

    @Autowired
    BootUserReposotory repository;

    @Test
    public void testRepository(){
        repository.save(new BootUser("duanjh", "Duanjh@123", "duanjh@qq.com"));
        repository.save(new BootUser("lucy", "lucy@123", "lucy@qq.com"));
        repository.save(new BootUser("kay", "Kay@123", "kay@qq.com"));

        Assert.assertEquals(3, repository.count());
        Assert.assertEquals("lucy@qq.com", repository.findByUsername("lucy").getEmail());
        repository.delete(repository.findByUsername("kay"));
    }
}
