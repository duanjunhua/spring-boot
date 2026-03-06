package com.duanjh;

import com.duanjh.jdbcTemplate.BootUserJdbcTemplate;
import com.duanjh.jpa.entity.BootUser;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-06 周五 15:40
 * @Version: v1.0
 * @Description:
 */
@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcTemplateTest {

    @Autowired
    BootUserJdbcTemplate template;

    @Test
    public void countTest(){
        Integer counts = template.counts();
        Assert.assertTrue(counts >= 0);
    }

    @Test
    public void selectTest(){
        List<BootUser> list = new ArrayList<BootUser>();
        list.add(new BootUser("test", "Test@123", "test@qq.com"));
        template.batchAdd(list);
        BootUser user = template.selectByName("test");
        Assert.assertEquals("test", user.getUsername());
    }

    @Test
    public void listTest(){
        Integer counts = template.counts();
        List<BootUser> list = template.list();
        Assert.assertEquals(counts.intValue(), CollectionUtils.size(list));
    }

    @Test
    public void updateTest(){
        List<BootUser> list = new ArrayList<BootUser>();
        list.add(new BootUser("test", "Test@123", "test@qq.com"));
        template.batchAdd(list);

        BootUser user = template.selectByName("test");
        Assert.assertEquals("test", user.getUsername());

        user.setUsername("test2");
        template.batchUpdate(Arrays.asList(user));

        Assert.assertEquals("test2", user.getUsername());

    }

}
