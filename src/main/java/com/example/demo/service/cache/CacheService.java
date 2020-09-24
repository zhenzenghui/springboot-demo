package com.example.demo.service.cache;

/**
 * @author zzh
 * @date 2020/7/6
 */
public interface CacheService {

    String get(String key);

    void set(String key, Object obj);

    void set(String key, Object obj, Long expireTime);

    Boolean delete(String key);

}
