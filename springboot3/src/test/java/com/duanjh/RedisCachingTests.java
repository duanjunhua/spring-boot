package com.duanjh;

import com.duanjh.jpa.entity.BootUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-06 周五 10:29
 * @Version: v1.0
 * @Description: Redis缓存测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCachingTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void stringTests(){
        stringRedisTemplate.opsForValue().set("hello", "world");
        Assert.assertEquals("world", stringRedisTemplate.opsForValue().get("hello"));
    }

    @Test
    public void objectTests() throws Exception {

        BootUser user = new BootUser("duanjh", "Duanjh@123", "duanjh@qq.com");
        ValueOperations<String, BootUser> vos = redisTemplate.opsForValue();

        // 长久存储
        vos.set("permanent_key", user);

        // 指定过期存储，如1秒后过期
        vos.set("temp_key", user, 1, TimeUnit.SECONDS);

        Thread.sleep(1000);

        boolean exist = redisTemplate.hasKey("temp_key");

        if (exist) {
            System.out.println("exist is true");
        }else{
            System.out.println("exist is false");
        }

        Assert.assertEquals("duanjh", vos.get("permanent_key").getUsername());
    }

    @Test
    public void sessionTests(){

    }

}
