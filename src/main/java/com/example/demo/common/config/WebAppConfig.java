package com.example.demo.common.config;

import com.example.demo.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * @author zzh
 * @date 2020/6/10
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                //拦截的路径
                .addPathPatterns("/**")
                //不需要拦截的路径
                .excludePathPatterns("/a","/b","/c");

        //如果有多个，继续写，根据顺序进行拦截
        //1.加入的顺序就是拦截器执行的顺序，
        //2.按顺序执行所有拦截器的preHandle
        //3.所有的preHandle 执行完再执行全部postHandle 最后是postHandle

    }

}
