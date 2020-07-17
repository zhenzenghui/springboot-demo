package com.example.demo.contoller;

import com.example.demo.common.aspect.ManagerTest;
import com.example.demo.service.cache.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zzh
 * @date 2020/6/9
 */

@RestController
//@Controller
@Api(value = "测试", description = "测试test")
public class Test {

    @Resource
    private CacheService cacheService;

    @ManagerTest
    @ApiOperation(value = "测试test1", notes = "测试test1。。。",httpMethod = "GET")
//    @RequestMapping("/test1/{k}/{v}")
    @GetMapping(value = "/test1")
    public String test1(@PathVariable String k, @PathVariable String v){
        //cacheService.set(k,v);
        System.out.println("k=" + k + "; v=" + v);
        return k;
    }

    @ApiOperation(value = "测试test2", notes = "测试test2。。。",httpMethod = "GET")
    @GetMapping(value = "test2")
    public String test2(String k){
        //String s = cacheService.get(k);
        System.out.println("k=" + k);
        return k;
    }

    @RequestMapping("/test3/{k}")
    public Boolean test3(@PathVariable String k){
        //cacheService.delete(k);
        System.out.println("k=" + k);
        return true;
    }

}
