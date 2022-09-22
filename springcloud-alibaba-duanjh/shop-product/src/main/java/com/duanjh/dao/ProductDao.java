package com.duanjh.dao;

import com.duanjh.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-07
 * @Version: V1.0
 * @Description: 商品服务
 */
public interface ProductDao extends JpaRepository<Product, Integer> {
}
