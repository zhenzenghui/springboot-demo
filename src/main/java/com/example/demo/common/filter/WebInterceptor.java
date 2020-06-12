package com.example.demo.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器，打印接口时间
 * @author zzh
 * @date 2020/6/10
 */
@Slf4j
public class WebInterceptor implements HandlerInterceptor {

//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String inteface = request.getServletPath();
//        StringBuffer sb = new StringBuffer();
//        sb.append("\r\n/***************************************************************请求日志记录start******************************************************************************************/");
//        sb.append("\r\n请求进入接口：" + inteface + ",请求进入时间：" + sdf.format(new Date()) + "===IP：" + request.getRemoteAddr());
//        sb.append("\r\n传入参数key-value >>> ");
//        //获取所有入参参数信息
//        Enumeration<?> enu = request.getParameterNames();
//        while (enu.hasMoreElements()) {
//            String paraName = (String) enu.nextElement();
//            sb.append("--" + paraName + ":" + request.getParameter(paraName));
//        }
//        log.info(sb.toString());
//        return true;
//    }
//
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        log.info("\r\n请求退出接口：" + request.getServletPath() + ",请求退出时间：" + sdf.format(new Date()) + "===IP：" + request.getRemoteAddr());
//        log.info("\r\n/****************************************************************请求日志记录end******************************************************************************************/");
//
//    }

}
