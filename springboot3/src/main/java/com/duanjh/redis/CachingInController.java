package com.duanjh.redis;

import com.duanjh.jpa.entity.BootUser;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-02-06 周五 10:41
 * @Version: v1.0
 * @Description: 自动根据方法生成缓存
 */
@Slf4j
@RestController
public class CachingInController {

    @Cacheable(value = "user-key")  // 指定缓存
    @RequestMapping("/getBootUser")
    public BootUser getBootUser(){
        BootUser user = new BootUser("duanjh", "Duanjh@123", "duanjh@qq.com");
        log.info("若调用时没有出现此日志且能打印出数据表示测试成功");
        return user;
    }

    @RequestMapping("/uid")
    public String uid(HttpSession session){
        UUID uid = (UUID)session.getAttribute("uid");

        if(ObjectUtils.isEmpty(uid)){
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);

        return session.getId();
    }
}
