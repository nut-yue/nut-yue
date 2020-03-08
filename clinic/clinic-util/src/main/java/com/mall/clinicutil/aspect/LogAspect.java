package com.mall.clinicutil.aspect;

import com.mall.clinicutil.annotation.UserAction;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName : LogAspect
 * @Date ：2019/10/25 9:56
 * @author：nut-yue
 */
@Aspect
@Component
public class LogAspect {

    private static Logger logger = Logger.getLogger(LogAspect.class);

    /**
     * 在每个controller方法执行之后获取自定义注解中的方法说明，输出日志
     *
     * @param joinPoint 切入点
     * @throws Throwable 异常
     */
    @After(value = "execution(* com.mall.controller.*.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) throws Throwable {

        // 获取类的全路径
        String classPath = joinPoint.getTarget().getClass().getName();
        // 获得类模版
        Class clazz = Class.forName(classPath);
        // 获取方法数组
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(joinPoint.getSignature().getName())) {
                // 获取自定义注解
                UserAction userAction = method.getAnnotation(UserAction.class);
                // 获得注解的内容
                String desc = userAction.action();
                // 得到当前方法名
                String methodName = joinPoint.getSignature().getName();
                // 输出日志
                logger.info("当前方法为：" + methodName + desc);
            }
        }
    }
}
