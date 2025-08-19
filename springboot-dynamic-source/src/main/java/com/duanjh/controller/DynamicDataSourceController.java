package com.duanjh.controller;

import com.duanjh.annotation.DS;
import com.duanjh.entity.TSlope;
import com.duanjh.entity.TUser;
import com.duanjh.mapper.SlopeMapper;
import com.duanjh.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2025-08-19 周二 10:01
 * @Version: v1.0
 * @Description: 动态切换数据源
 */
@RestController
@RequestMapping("/dynamic-datasource")
public class DynamicDataSourceController {

    @Resource
    UserMapper userMapper;

    @Resource
    SlopeMapper slopeMapper;

    @DS("typt")
    @GetMapping("/typt/user")
    public List<TUser> getTyptUserList() {
        return userMapper.selectList(null);
    }

    @DS("yhzs")
    @GetMapping("/yhzs/slope")
    public List<TSlope> getYhzsSlopeList() {
        return slopeMapper.selectList(null);
    }

}
