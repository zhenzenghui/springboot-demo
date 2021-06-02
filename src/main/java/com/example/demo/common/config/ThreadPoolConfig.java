package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zzh
 * @date 2021/4/2
 */
@Configuration
public class ThreadPoolConfig {

    //核心线程数
    private static final int CORE_POOL_SIZE = 5;

    //最大线程数
    private static final int MAX_POOL_SIZE = 10;

    //队列长度
    private static final int QUEUE_CAPACITY = 999;

    //线程存活时间
    private static final int KEEP_ALIVE_SECOND = 60;

    @Bean(value = "taskExecutor")
    public Executor taskExecutor(){

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new VisiableThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        threadPoolTaskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        threadPoolTaskExecutor.setKeepAliveSeconds(KEEP_ALIVE_SECOND);
        //拒绝策略 任务已满时，由调用者线程执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程池中的线程前缀名
        threadPoolTaskExecutor.setThreadNamePrefix("component-biz-service-");
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;

    }



}
