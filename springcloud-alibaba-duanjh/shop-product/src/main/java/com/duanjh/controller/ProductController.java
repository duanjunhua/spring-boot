package com.duanjh.controller;

import com.alibaba.fastjson.JSON;
import com.duanjh.entity.Product;
import com.duanjh.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-07
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@RestController
public class ProductController {

    ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{pid}")
    public Product product(@PathVariable("pid") Integer pid){
        Product product = productService.findByPid(pid);
        log.info("查询的商品： {}", JSON.toJSONString(product));
        return product;
    }
}
