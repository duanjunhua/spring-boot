package com.duanjh.service.impl;

import com.duanjh.dao.OrderDao;
import com.duanjh.entity.Order;
import com.duanjh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-07
 * @Version: V1.0
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }
}
