package com.cbd.demo.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName : AroundUtils
 * @Date ：2019/6/5/0005 20:34
 * @Desc ：类的介绍：
 * @author：张皓
 */
@Component
@Aspect
public class AroundUtils {
    @Autowired
    private  PerformaceUtil performaceUtil;
    @Around("execution(* com.cbd.demo.controller.*.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint)throws Throwable{
        performaceUtil.setPerformace(joinPoint.getSignature().getName());
        System.out.println("这是环绕增强代码《==前==》");

        //调用目标
        Object object = joinPoint.proceed();

        System.out.println("这是环绕增强代码《==后==》");
        performaceUtil.printPerformace(joinPoint);
        return object;
    }
}
