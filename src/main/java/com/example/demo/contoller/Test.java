package com.example.demo.contoller;

import com.example.demo.service.cache.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author zzh
 * @date 2020/6/9
 */

//@RestController
@Controller
@Api(value = "测试", description = "测试test")
@Slf4j
public class Test {

    @Resource
    private CacheService cacheService;

    @ApiOperation(value = "测试test1", notes = "测试test1。。。",httpMethod = "GET")
//    @RequestMapping("/test1/{k}/{v}")
    @GetMapping("/test1")
    @ResponseBody
    public String test1(String k, String v){
        //cacheService.set(k,v);
        try {

            log.info("-------k={},v={}",k,v);
            System.out.println("k=" + k + "; v=" + v);
        }catch (Exception e) {
            log.error("e============",e);
        }
        return k;
    }

    @ApiOperation(value = "测试test2", notes = "测试test2。。。",httpMethod = "GET")
    @GetMapping(name = "test2")
    public String test2(String k){
        //String s = cacheService.get(k);
        System.out.println("k=" + k);
        return k;
    }

    @GetMapping("/test3/{k}")
    @ResponseBody
    public Boolean test3(@PathVariable String k){
        //cacheService.delete(k);
        System.out.println("k=" + k);
        return true;
    }

}
