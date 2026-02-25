package com.duanjh;

import com.duanjh.mongo.multisource.primary.singlesource.MongoUser;
import com.duanjh.mongo.multisource.primary.singlesource.MongoUserRepository;
import com.duanjh.mongo.multisource.primary.singlesource.MongoUserService;
import com.duanjh.mongo.multisource.primary.PrimaryRepository;
import com.duanjh.mongo.multisource.primary.PrimaryUser;
import com.duanjh.mongo.multisource.secondary.SecondaryRepository;
import com.duanjh.mongo.multisource.secondary.SecondaryUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-10 周二 14:27
 * @Version: v1.0
 * @Description:
 */
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoRepositoryTests {

    @Autowired
    MongoUserRepository repository;

    @Autowired
    MongoUserService service;

    @Autowired
    PrimaryRepository primaryRepository;

    @Autowired
    SecondaryRepository secondaryRepository;

    /**
     * 默认接口方式
     */
    @Test
    public void saveUserTest(){
        MongoUser user = repository.save(new MongoUser("duanjh", "Duanjh@123", "duanjh@qq.com"));
        Assert.assertNotNull(user.getId());

        repository.deleteById(user.getId());
        Assert.assertTrue(repository.findById(user.getId()).isEmpty());
    }

    /**
     * 自定义Service方式
     */
    @Test
    public void serviceTest(){
        MongoUser user = service.saveUser(new MongoUser("lucy", "Lucy@123", "lucy@qq.com"));
        Assert.assertNotNull(user.getId());

        MongoUser userByName = service.findByUsername("lucy");
        Assert.assertNotNull(userByName);
        Assert.assertEquals(user.getId(), userByName.getId());

        user.setUsername("kay");
        user.setPassword("kay@123");
        long updateRows = service.updateUser(user);
        Assert.assertEquals(1L, updateRows);

        service.deleteUser(user.getId());

        Assert.assertTrue(repository.findById(user.getId()).isEmpty());

    }

    /**
     * 多数据源方式
     */
    @Test
    public void multiDsTest(){

        PrimaryUser primary = primaryRepository.save(new PrimaryUser(    "duanjh", "Duanjh@123", "duanjh@qq.com"));
        Assert.assertNotNull(primary.getId());

        primaryRepository.deleteById(primary.getId());
        Assert.assertTrue(primaryRepository.findById(primary.getId()).isEmpty());

        SecondaryUser secondary = secondaryRepository.save(new SecondaryUser("duanjh", "Duanjh@123", "duanjh@qq.com"));
        Assert.assertNotNull(secondary.getId());

        secondaryRepository.deleteById(secondary.getId());
        Assert.assertTrue(secondaryRepository.findById(secondary.getId()).isEmpty());
    }
}
