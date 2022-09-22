package com.duanjh.sentinel.fallback;

import com.duanjh.entity.Product;
import com.duanjh.rest.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-07
 * @Version: V1.0
 * @Description: 创建容错类：容错类要求必须实现被容错的接口,并为每个方法实现容错方案
 */
@Slf4j
@Component
public class ProductServiceFallback implements ProductService {
    @Override
    public Product findById(Integer pid) {
        Product product = new Product();
        product.setPid(-1);
        return product;
    }
}
