package com.example.demo.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zzh
 * @date 2021/4/6
 */
@Slf4j
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    private void showThreadPoolInfo(String prefix){

        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        if (null == threadPoolExecutor) {
            return;
        }

        log.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.getThreadNamePrefix(),
                prefix,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size()
                );
    }

    @Override
    public void execute(Runnable task){
        showThreadPoolInfo("do execute");
        super.execute(task);
    }

    @Override
    public Future<?> submit(Runnable task){
        showThreadPoolInfo("do submit");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task){
        showThreadPoolInfo("do submit");
        return super.submit(task);
    }


}
