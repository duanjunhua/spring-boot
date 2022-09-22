package com.duanjh.service;

import com.duanjh.entity.Product;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-07
 * @Version: V1.0
 * @Description:
 */
public interface ProductService {

    public Product findByPid(Integer pid);
}
