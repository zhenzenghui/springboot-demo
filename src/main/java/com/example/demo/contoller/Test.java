package com.example.demo.contoller;

import com.example.demo.service.cache.CacheService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zzh
 * @date 2020/6/9
 */

@RestController
public class Test {

    @Resource
    private CacheService cacheService;

    @RequestMapping("/test1/{k}/{v}")
    public String test1(@PathVariable String k, @PathVariable String v){
        cacheService.set(k,v);
        return k;
    }

    @RequestMapping("/test2/{k}/{v}")
    public String test2(@PathVariable String k){
        String s = cacheService.get(k);
        return s;
    }

    @RequestMapping("/test3/{k}")
    public Boolean test3(@PathVariable String k){
        cacheService.delete(k);
        return true;
    }

}
