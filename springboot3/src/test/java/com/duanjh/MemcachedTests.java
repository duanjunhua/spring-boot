package com.duanjh;

import com.duanjh.memcached.MemcachedRunner;
import jakarta.annotation.Resource;
import net.spy.memcached.MemcachedClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-02 周一 16:16
 * @Version: v1.0
 * @Description: Memcached缓存测的是
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemcachedTests {

    @Resource
    MemcachedRunner runner;

    @Test
    public void memcachedGetAndSetTest(){

        MemcachedClient client = runner.getClient();

        // Set
        client.set("CACHE_KEY", 1000, "CACHE_VALUE");

        // Get
        Assert.assertEquals("CACHE_VALUE", client.get("CACHE_KEY"));
    }

}
