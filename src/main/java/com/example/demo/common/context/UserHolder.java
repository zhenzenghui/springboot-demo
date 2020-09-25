package com.example.demo.common.context;

import java.util.Map;

/**
 * 用户上下文
 * @author zzh
 * @date 2020/9/24
 */
public class UserHolder {

    private static final ThreadLocal<Map<String, String>> LOGIN_THREADLOCAL = new ThreadLocal<>();

    /**
     * 存入用户上下文
     * @param userMap
     */
    public static void map(Map<String, String> userMap){
        LOGIN_THREADLOCAL.set(userMap);
    }

    /**
     * 清除用户上下文信息
     */
    public static void clean(){
        LOGIN_THREADLOCAL.remove();
    }

    /**
     * 姓名
     * @return
     */
    public static String userName(){
        return get("userName");
    }

    /**
     * userId
     * @return
     */
    public static String userId(){
        return get("userId");
    }

    /**
     * 根据key获取用户信息
     * @param key
     * @return
     */
    public static String get(String key){
        Map<String, String> map = LOGIN_THREADLOCAL.get();
        return map.get(key);
    }

    /**
     * 用户信息
     * @return
     */
    public static Map<String, String> getUserMap(){
        return LOGIN_THREADLOCAL.get();
    }

}
