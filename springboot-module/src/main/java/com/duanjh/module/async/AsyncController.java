package com.duanjh.module.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-06-06 周四 17:23
 * @Version: v1.0
 * @Description: 2. 异步调用
 */
@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    AsyncService service;

    @GetMapping("/void-method")
    public String callVoidAsyncMethod(){
        service.asyncMethod();
        return "Async method called";
    }

    @GetMapping("/return-method")
    public String callReturnAsyncMethod() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = service.asyncMethodWithReturn();

        // 阻塞返回
        while(!future.isDone()){
            log.info("Wait to return: {}........", future.get());
        }

        return "Async method not Ok";
    }
}
