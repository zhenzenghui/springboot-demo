package com.example.demo.service.cache;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zzh
 * @date 2020/7/6
 */
@Service
public class CacheServiceImpl implements CacheService{

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 获取缓存
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        Object obj = stringObjectValueOperations.get(key);
        return JSONObject.toJSONString(obj);

    }

    /**
     * 设置缓存
     * @param key
     */
    @Override
    public void set(String key, Object obj) {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        stringObjectValueOperations.set(key, obj);

    }

    @Override
    public void set(String key, Object value, Long expireTime){
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        stringObjectValueOperations.set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 删除缓存
     * @param key
     */
    @Override
    public Boolean delete(String key) {
        Boolean bol = false;
        if (!StringUtils.isEmpty(key)) {
            bol = redisTemplate.delete(key);
        }
        return bol;
    }
}
