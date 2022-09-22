package com.duanjh.service.impl;

import com.duanjh.dao.ProductDao;
import com.duanjh.entity.Product;
import com.duanjh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-07
 * @Version: V1.0
 * @Description:
 */
@Service
public class ProductServiceImpl implements ProductService {

    ProductDao productDao;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product findByPid(Integer pid) {
        Optional<Product> res = productDao.findById(pid);
        return res.isPresent() ? res.get() : null;
    }
}
