package com.duanjh.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-03-16 周一 09:18
 * @Version: v1.0
 * @Description:
 */
@RestController
@RequestMapping("/ex")
public class PersonalExceptionController {

    @GetMapping("/{val}")
    public ResponseEntity<String> exception(@PathVariable("val") Integer val){
        try {
            int division = 10 / val;
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            throw new PersonalException("抛出自定义异常");
        }
    }
}
