package com.cbd.demo.annotation;

import com.cbd.demo.entity.PerformanceEntity;
import com.cbd.demo.service.IPerformanceService;
import com.cbd.demo.util.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @ClassName : PerformceUtil
 * @Date ：2019/6/5/0005 20:37
 * @Desc ：类的介绍：
 * @author：张皓
 */

@Component
public class PerformaceUtil {
    @Autowired
    private IPerformanceService performanceService;
    //方法执行开始的系统时间
    private long begin;
    //方法执行结束系统时间
    private long end;
    //执行的方法名
    private String methodName;

    public void setPerformace(String name){
        this.methodName=name;
        this.begin= System.currentTimeMillis();

    }
    //打印性能统计结果
    public void printPerformace(JoinPoint joinPoint){
        // 得到类的全路径
        String classType = joinPoint.getTarget().getClass().getName();
        // 得到类模板
        Class clazz = null;
        try {
            clazz = Class.forName(classType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 得到方法数组
        Method[] methods = clazz.getMethods();
        String desc=null;
        // 遍历数组
        for (Method method : methods) {
            // 判断方法名是否等于当前正在运行的方法名
            if(method.getName() == joinPoint.getSignature().getName()) {
                // 判断注释是否为自定义注解
                if (method.isAnnotationPresent(Introduce.class)) {
                    // 获得自定义注解对象
                    Introduce introduce = method.getAnnotation(Introduce.class);
                    // 获得注解的内容
                    desc = introduce.desc();
                }
            }
        }
        this.end = System.currentTimeMillis();
        long elapse = end -begin;
        PerformanceEntity performanceEntity = new PerformanceEntity();
        performanceEntity.setPerformanceTime(Math.toIntExact(elapse));
        performanceEntity.setPerformanceType(desc);
        performanceEntity.setPerformanceDate(DateUtils.getDate());
        performanceService.addPerformance(performanceEntity);
        System.out.println(methodName+"方法执行耗时:"+elapse+"毫秒");
    }
}
