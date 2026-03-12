package com.duanjh;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.jpa.repository.BootUserRepository;
import com.duanjh.shiro.config.PersonalRealm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-05 周四 16:41
 * @Version: v1.0
 * @Description: 持久类测试
 */
@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BootUserRepositoryTests {

    @Autowired
    BootUserRepository repository;

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

    private void initParamValue(){
        repository.save(new BootUser("test001", "test@123", "test001@qq.com"));
        repository.save(new BootUser("test002", "test@123", "test002@qq.com"));
        repository.save(new BootUser("test003", "test@123", "test003@qq.com"));
        repository.save(new BootUser("test004", "test@123", "test004@qq.com"));
    }

    @Test
    public void repositoryTest(){
        long countExist = repository.count();
        initParamValue();

        Assert.assertEquals(4 + countExist, repository.count());
        Assert.assertEquals("test002@qq.com", repository.findByUsername("test002").getEmail());
        repository.deleteByUsername("test");
    }

    @Test
    public void JPQLTest(){
        initParamValue();
        BootUser u = repository.findByUserNameEamil("test001", "test001@qq.com");

        Assert.assertEquals("test001@123", u.getPassword());
    }

    @Test
    public void pageableQueryTest(){
        Pageable pageable = Pageable.ofSize(3).withPage(1);

        initParamValue();

        Page<BootUser> pageUser = repository.pageQuery(1l, pageable);

        Assert.assertNotNull(pageUser.getTotalPages());
    }

    @Test
    public void firstOrTopLimitQueryTest(){
        initParamValue();

        // 根据名字倒叙查询前三
        List<BootUser> top3 = repository.findTop3ByPassword("test@123",Sort.by("username").descending());

        Assert.assertEquals(3, CollectionUtils.size(top3));
        Assert.assertTrue(top3.stream().filter(u -> u.getUsername().equals("test@test001")).findFirst().isEmpty());


        BootUser u = repository.findFirstByOrderByUsernameDesc();

        Assert.assertEquals("test004", u.getUsername());
    }

    /**
     * 流式查询
     */
    @Test
    public void streamQueryTest(){
        initParamValue();

        Stream<BootUser> us = repository.findAllByEmailNotNull();

        us.forEach(u -> System.out.println(u.getUsername()));

    }

    @Test
    public void futureQueryTest() throws Exception {
        initParamValue();

        Future<BootUser> fu = repository.findOneByUsername("test001");

        if(fu.isDone()){
            Assert.assertEquals("test001", fu.get().getUsername());
        }
    }
}
