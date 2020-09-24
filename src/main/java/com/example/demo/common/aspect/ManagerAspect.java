package com.example.demo.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author zzh
 * @date 2020/7/17
 */
public class ManagerAspect {

    @Pointcut("@annotation(ManagerTest)")
    public void managetPonitCut(){

    }

    @Before(value = "managetPonitCut()")
    public void doBefore(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        System.out.println("目标方法名：" + signature.getName());
        System.out.println("目标方法所属类的简单类名：" + signature.getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名：" + signature.getDeclaringTypeName());
        System.out.println("目标方法声明类型：" + signature.getModifiers());
        ManagerTest annotation = signature.getMethod().getAnnotation(ManagerTest.class);
        System.out.println("注解value=" + annotation.value() + "; " + "name = " + annotation.name());
        System.out.println("方法开始之前。。。。。。。。。。。。");
    }

    @After(value = "managetPonitCut()")
    public void doAfter(){
        System.out.println("方法结束之后。。。。。。。。。。。。");
    }


}
