package com.duanjh.langchain.tool;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.jpa.repository.BootUserRepository;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-19 周二 15:39
 * @Version: v1.0
 * @Description:
 */
@Component
public class BootUserTool {

    private final BootUserRepository repository;

    public BootUserTool(BootUserRepository repository) {
        this.repository = repository;
    }

    @Tool("根据用户名查询用户信息")
    public BootUser getBootUser(@P("用户名") String username) {
        return repository.findByUsername(username);
    }
}
