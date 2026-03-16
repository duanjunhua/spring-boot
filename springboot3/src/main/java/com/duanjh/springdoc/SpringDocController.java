package com.duanjh.springdoc;

import com.duanjh.jpa.entity.BootUser;
import com.duanjh.jpa.repository.BootUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-16 周一 10:04
 * @Version: v1.0
 * @Description:
 */
@Tag(name = "SpringDocController",description = "SpringDoc样例")
@RestController
@RequestMapping("/docs")
public class SpringDocController {

    @Autowired
    BootUserRepository repository;


    @Operation(summary ="获取用户信息",  description = "返回用户列表", parameters = {
            @Parameter(name = "username", description = "用户名"),
            @Parameter(name = "id", description = "用户ID")
    })
    @GetMapping("/")
    public ResponseEntity<List<BootUser>> list() {
        return ResponseEntity.ok(repository.findAll());
    }
}
