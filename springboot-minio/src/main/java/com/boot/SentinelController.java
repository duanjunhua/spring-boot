package com.boot;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel")
public class SentinelController {

    /**
     * 限流接口
     */
    @SentinelResource(value = "limit", blockHandler = "limited")
    @GetMapping("/limit")
    public String limit(){
        return "Sentinel 限流请求";
    }

    @GetMapping("/release")
    public String release(){
        return "开放式请求";
    }

    /**
     * 自定义限流处理逻辑，注意：必须与限流的方法参数一致，外加BlockException
     */
    public String limited(BlockException e){
        return "Blocked By Sentinel (Flow limiting)";
    }

}
