package com.duanjh.dao;

import com.duanjh.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-07
 * @Version: V1.0
 * @Description:
 */
public interface OrderDao extends JpaRepository<Order, Integer> {
}
