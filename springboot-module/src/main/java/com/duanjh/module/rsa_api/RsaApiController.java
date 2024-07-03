package com.duanjh.module.rsa_api;

import cn.shuibo.annotation.Decrypt;
import cn.shuibo.annotation.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2024-05-27 周一 14:13
 * @Version: v1.0
 * @Description: RSA加密API，RSA：非对称加密
 */
@Slf4j
@RestController
@RequestMapping("/rsa_api")
public class RsaApiController {

    RestTemplate restTemplate = new RestTemplate();

    @Encrypt
    @GetMapping("/encrypt")
    public Map<String, Object> encryptApiData(){
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Michael");
        data.put("age", "30");
        return  data;
    }

    @Decrypt
    @PostMapping(value = "/decrypt")
    public Object decrypt(@RequestBody Object data){
        log.info("解密的加密数据：{}", data);
        return data;
    }

    @GetMapping("/remote-access")
    private Map<String, Object> remoteAccess(){
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/rsa_api/encrypt", String.class);
        if(ObjectUtils.isEmpty(entity)) return new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> body =new HttpEntity<>(entity.getBody(), headers);
        log.info("加密数据：{}", entity.getBody());
        ResponseEntity<Map> desEntity = restTemplate.postForEntity("http://localhost:8080/rsa_api/decrypt", body, Map.class);
        return desEntity.getBody();
    }
}
