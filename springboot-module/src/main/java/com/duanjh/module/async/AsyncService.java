package com.duanjh.module.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-06-06 周四 17:16
 * @Version: v1.0
 * @Description: 1. 异步服务
 */
@Slf4j
@Service
public class AsyncService {

    /**
     * 不带返回值的方法
     */
    @Async("taskExecutor")  // 标注异步方法，指定使用自定义线程池：taskExecutor
    public void asyncMethod(){
      try {
          Thread.sleep(5000);
          log.info("Async method is called.");
      } catch (Exception e) {
          log.error("Async method Exception: {}", e.getLocalizedMessage());
      }
    }

    /**
     * 带返回值的异步方法
     */
    @Async("taskExecutor")
    public CompletableFuture<String> asyncMethodWithReturn(){
        try {
            Thread.sleep(5000);
            log.info("Async method is called.");
            return CompletableFuture.completedFuture("Async method is success called");
        } catch (Exception e) {
            log.error("Async method Exception: {}", e.getLocalizedMessage());
        }
        return CompletableFuture.completedFuture("Async method called Failed.");
    }

}
