package com.duanjh.controller;

import com.alibaba.fastjson.JSON;
import com.duanjh.entity.Order;
import com.duanjh.entity.Product;
import com.duanjh.rest.ProductService;
import com.duanjh.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-09-07
 * @Version: V1.0
 * @Description:
 */
@Slf4j
@RestController
public class OrderController {

    RestTemplate restTemplate;

    OrderService orderService;

    DiscoveryClient discoveryClient;

    /**
     * Feign Service
     */
    ProductService productService;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Resource
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid){
        log.info(">>客户下单，这时候要调用商品服务查询商品信息");
        Order order = new Order();
        //1. 通过RestTemplate调用商品服务
        // Product product = restTemplate.getForObject("http://localhost:8081/product/" + pid, Product.class);
        // log.info(">>商品信息，查询结果：{}", JSON.toJSONString(product));
        // order.setUid(1);

        //2. 使用微服务方式调用，service-product为注册到Nacos的服务名称
        //ServiceInstance instance = discoveryClient.getInstances("service-product").get(0);
        //3.实现负载均衡
        // List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        // int index = new Random().nextInt(instances.size());
        // ServiceInstance instance = instances.get(index);

        // String url = instance.getHost() + ":" + instance.getPort();
        // log.info(">> 从Nacos中获取到的微服务地址： {}", url);
        // Product product = restTemplate.getForObject("http://" + url+ "/product/" + pid, Product.class);
        // log.info(">>商品信息，查询结果：{}", JSON.toJSONString(product));

        //4. 基于Ribbon，使用RestTemplate的@LoadBalanced注解实现负载均衡
        /**
         * Ribbon内置了多种负载均衡策略,内部负载均衡的顶级接口为: com.netflix.loadbalancer.IRule
         * 可通过配置文件来修改负载均衡策略：{服务提供者的mingcheng}.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule
         *  BestAvailableRule：选择一个最小的并发请求的server
         *  AvailabilityFilteringRule: 过滤掉那些因为一直连接失败的被标记为circuit tripped的后端server，并过滤掉那些高并发的的后端server（activeconnections 超过配置的阈值）
         *  WeightedResponseTimeRule: 根据相应时间分配一个weight，相应时间越长，weight越小，被选中的可能性越低。
         *  RetryRule: 对选定的负载均衡策略机上重试机制
         *  RoundRobinRule： 轮询方式轮询选择server
         *  RandomRule: 随机选择一个server
         *  ZoneAvoidanceRule： 复合判断server所在区域的性能和server的可用性选择server
         *
         */
        // String url = "service-product";
        // Product product = restTemplate.getForObject("http://" + url+ "/product/" + pid, Product.class);
        // log.info(">>商品信息，查询结果：{}", JSON.toJSONString(product));

        //5.基于Feign实现负载均衡
        Product product = productService.findById(pid);
        log.info(">>商品信息，查询结果：{}", JSON.toJSONString(product));


        //集成Sentinel
        if(product.getPid() == -1){
            Order order1 = new Order();
            order1.setPname("下单失败");
            return order1;
        }

        order.setUid(2);
        order.setUsername("Test01");
        order.setPid(product.getPid());
        order.setPname(product.getPname());
        order.setPprice(product.getPprice());
        order.setNumber(1);
        orderService.save(order);
        return order;
    }
}
