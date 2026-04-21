package com.duanjh.modulith;

import com.duanjh.modulith.product.ProductService;
import com.duanjh.modulith.product.internal.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-15 周三 16:41
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/modulith")
public class ModulithController {

    @Autowired
    ProductService service;

    @GetMapping("/publish")
    public String publishNotify(){
        service.publish(new Product("Duanjh", "Publish Message", 1));
        return "success";
    }
}
