package com.duanjh;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.duanjh.mybatisplus.entity.MpUser;
import com.duanjh.mybatisplus.mapper.MpUserMapper;
import com.duanjh.shiro.domain.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-06 周五 16:14
 * @Version: v1.0
 * @Description:
 */
@Transactional  // 确保测试数据回滚
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MybatisMapperTests {

    @Autowired
    @Qualifier("mpUserMapper")
    MpUserMapper userMapper;

    @Test
    public void insertTest(){
        int exists = userMapper.getList().size();
        userMapper.insert(new MpUser("zhangsan", "Zhangs@123", "zs@qq.com"));
        userMapper.insert(new MpUser("michale", "Michale@123", "Michale@qq.com"));
        userMapper.insert(new MpUser("wangwu", "Wangw@123", "ww@qq.com"));

        Assert.assertEquals(3 + exists, userMapper.getList().size());
    }

    @Test
    public void queryTest(){
        userMapper.insert(new MpUser("zhangsan", "Zhangs@123", "zs@qq.com"));
        userMapper.insert(new MpUser("michale", "Michale@123", "Michale@qq.com"));
        userMapper.insert(new MpUser("wangwu", "Wangw@123", "ww@qq.com"));
        MpUser zhangsan = userMapper.findMpUserByUsername("zhangsan");
        System.out.println(zhangsan.getEmail());
        Assert.assertEquals("zs@qq.com", zhangsan.getEmail());
    }

    @Test
    public void pageTest(){
        System.out.println("MP自带分页查询");
        userMapper.insert(new MpUser("zhangsan", "Zhangs@123", "zs@qq.com"));
        userMapper.insert(new MpUser("michale", "Michale@123", "Michale@qq.com"));
        userMapper.insert(new MpUser("wangwu", "Wangw@123", "ww@qq.com"));

        IPage<MpUser> page = new Page<>(1, 2);

        QueryWrapper<MpUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("email", "%qq.com");
        IPage userPage = userMapper.selectPage(page, queryWrapper);

        System.out.println("总条数：" + userPage.getTotal());
        System.out.println("当前页：" + userPage.getCurrent());
        System.out.println("每页显示数：" + userPage.getSize());

    }
}
