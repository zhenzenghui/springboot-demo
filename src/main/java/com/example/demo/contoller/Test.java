package com.example.demo.contoller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzh
 * @date 2020/6/9
 */

@RestController
public class Test {

    @RequestMapping("/test1/{ss}")
    public String test1(@PathVariable String ss){
        return ss;
    }

}
