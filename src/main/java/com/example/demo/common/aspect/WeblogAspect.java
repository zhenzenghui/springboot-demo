package com.example.demo.common.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 打印接口参数和耗时
 * @author zzh
 * @date 2020/6/9
 */
@Component
@Aspect
@Slf4j
public class WeblogAspect {

    private static final ThreadLocal<Long> THREAD_LOCAL_TIMESTAMP = new ThreadLocal<>();

    private static final int FIVE_SECONDS = 5 * 1000;

    @Pointcut("execution( * com.example.demo.contoller..*.*(..))")
    public void webLog(){

    }

    @Before(value = "webLog()")
    public void doBefore(JoinPoint joinPoint){
        this.getThreadLocalTimestamp();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        log.info("********************************************** request path : " + request.getRequestURI() + ", param : "
                + JSON.toJSONString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "webLog()")
    public void doAfterReturning(){
        long cost = System.currentTimeMillis() - this.getThreadLocalTimestamp();
        if (cost > FIVE_SECONDS) {
            log.warn("********************************************** warn  cost(ms) time :" + cost);
        } else {
            log.info("********************************************** cost(ms) time :" + cost);
        }
        THREAD_LOCAL_TIMESTAMP.remove();
    }

    @AfterThrowing(value = "webLog()")
    public void doAfterThrowing(){
        THREAD_LOCAL_TIMESTAMP.remove();
    }

    private long getThreadLocalTimestamp(){
        Long time = THREAD_LOCAL_TIMESTAMP.get();
        if (time != null) {
            return time;
        }
        long currentTimeMillis = System.currentTimeMillis();
        THREAD_LOCAL_TIMESTAMP.set(currentTimeMillis);
        return currentTimeMillis;

    }

}
