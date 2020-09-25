package com.example.demo.common.interceptor;

import com.example.demo.common.context.UserHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zzh
 * @date 2020/9/24
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //token校验
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            //token为空，跳转到登录页
            response.sendRedirect("{登录页}");
            return false;
        }

        //根据token 获取 用户的信息
        Map<String, String> userMap = this.getUserInfo(token);

        //放在threadLocal中
        UserHolder.map(userMap);

        return true;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空 threadLocal
        UserHolder.clean();
    }

    private Map<String, String> getUserInfo(String token) {
        //todo
        return null;
    }

}
