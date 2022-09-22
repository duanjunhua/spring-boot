package com.duanjh.rest;

import com.duanjh.entity.Product;
import com.duanjh.sentinel.fallback.ProductServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-07
 * @Version: V1.0
 * @Description:
 */
// @FeignClient("service-product") //声明调用的提供者的name，即服务名
@FeignClient(value = "service-product", fallback = ProductServiceFallback.class)    //value用于指定调用nacos下哪个微服务，fallback用于指定容错类
public interface ProductService {

    @GetMapping(value = "/product/{pid}")
    Product findById(@PathVariable("pid") Integer pid);
}
