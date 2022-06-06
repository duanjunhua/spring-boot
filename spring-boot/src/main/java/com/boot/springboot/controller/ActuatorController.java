package com.boot.springboot.controller;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan
 * @Date: 2022-06-02
 * @Version: V1.0
 * @Description: Actuator监控
 */
@RestController
public class ActuatorController {

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping("/visit")
    public String visitCount(){
        meterRegistry.counter("visit.count");
        return "visit Actuator Restful";
    }
}
