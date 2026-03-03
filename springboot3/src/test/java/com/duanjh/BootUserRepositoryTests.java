package com.duanjh;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.jpa.repository.BootUserReposotory;
import com.duanjh.shiro.config.PersonalRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Assert;
import org.junit.Before;
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

    @Autowired
    PersonalRealm personalRealm;

    /**
     * 当启用JPA的@CreatedBy等后需要初始化
     */
    @Before
    public void initSecurity(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(personalRealm);
        // 将 securityManager 放到 SecurityUtils 中
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void testRepository(){
        long countExist = repository.count();
        repository.save(new BootUser("test001", "test001@123", "test001@qq.com"));
        repository.save(new BootUser("test002", "test002@123", "test002@qq.com"));
        repository.save(new BootUser("test003", "test003@123", "test003@qq.com"));

        Assert.assertEquals(3 + countExist, repository.count());
        Assert.assertEquals("test002@qq.com", repository.findByUsername("test002").getEmail());
        repository.deleteByUsername("test");
    }
}
