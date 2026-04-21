package com.duanjh.modulith.product.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-15 周三 14:45
 * @Version: v1.0
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String name;

    private String description;

    private int price;

}
