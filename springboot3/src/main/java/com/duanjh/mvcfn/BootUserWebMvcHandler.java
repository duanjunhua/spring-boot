package com.duanjh.mvcfn;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.jpa.repository.BootUserReposotory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-05 周四 16:33
 * @Version: v1.0
 * @Description: 函数式编程模型，其中函数用于路由和处理请求，是基于注解的编程模型的替代方案
 */
@Slf4j
@Component
public class BootUserWebMvcHandler {

    @Autowired
    BootUserReposotory reposotory;

    public ServerResponse listUsers(ServerRequest request) {
        log.info("listUsers");
        List<BootUser> list = reposotory.findAll();
        return ServerResponse.ok().body(list);
    }

    public ServerResponse findUser(ServerRequest request) {
        String username = request.pathVariable("username");
        log.info("findUser，username: {}", username);
        if(StringUtils.hasText(username)) {
            BootUser user = reposotory.findByUsername(username);
            return ServerResponse.ok().body(user);
        }
        return ServerResponse.ok().build();
    }
}
