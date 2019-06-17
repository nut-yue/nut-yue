package com.cbd.demo.annotation;

import com.cbd.demo.bean.*;
import com.cbd.demo.service.ILogService;
import com.cbd.demo.util.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;


/**
 * @ClassName : Aspect
 * @Date ：2019/6/3 13:45
 * @Desc ：类的介绍：
 * @author：岳超
 */

@Aspect //该注解标示该类为切面类
@Component //注入依赖
public class MyAspect{

    @Autowired
    ILogService logService;

    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @After("execution(* com.cbd.demo.controller.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) throws Throwable {
        // 得到类的全路径
        String classType = joinPoint.getTarget().getClass().getName();
        // 得到类模板
        Class clazz = Class.forName(classType);
        // 得到方法数组
        Method[] methods = clazz.getMethods();
        // 遍历数组
        for (Method method : methods) {
            // 判断方法名是否等于当前正在运行的方法名
            if(method.getName() == joinPoint.getSignature().getName()) {
                // 判断注释是否为自定义注解
                if (method.isAnnotationPresent(Introduce.class)) {
                    // 获得自定义注解对象
                    Introduce introduce = method.getAnnotation(Introduce.class);
                    // 得到Request
                    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                    // 获得Session
                    HttpSession session = request.getSession();
                    // 获得ip
                    String ip = getIpAddress(request);
                    // 获得当前方法名
                    String methodName = joinPoint.getSignature().getName();
                    // 获得注解的内容
                    String desc = introduce.desc();
                    // 判断当前登陆用户类型
                    if(session.getAttribute("login") instanceof UserBean) {
                         // 从session中获取登陆用户
                         UserBean userBean = (UserBean) session.getAttribute("login");
                         // 调用方法添加日志
                         log(userBean.getAdminBean().getAdminRoleType(),userBean.getUserRealName(),ip,userBean.getAdminBean(),methodName,desc);
                    } else if(session.getAttribute("login") instanceof CompanyBean) {
                        // 从session中获取企业对象
                        CompanyBean companyBean = (CompanyBean) session.getAttribute("login");
                        // 调用方法添加日志
                        log(companyBean.getAdminBean().getAdminRoleType(),companyBean.getCompanyName(),ip,companyBean.getAdminBean(),methodName,desc);
                    } else if(session.getAttribute("login") instanceof AdministratorBean){
                        // 从session中获取对象
                        AdministratorBean admin = (AdministratorBean) session.getAttribute("login");
                        // 调用方法添加日志
                        log(admin.getAdminBean().getAdminRoleType(),admin.getAdministratorName(),ip,admin.getAdminBean(),methodName,desc);
                    }

                }
            }
        }
    }

    public void log(int adminType,String realName,String ip,AdminBean adminBean,String methodName,String desc){
        // 创建类型字符串由于日志分类字段
        String type = "";
        // 创建日志对象
        LogBean logBean = new LogBean();
        if (adminType == 1 || adminType == 2) {
            type += "前台";
        } else {
            type += "后台";
        }
        if ("getAdmin".equals(methodName)) {
            type += "登陆";
        } else {
            type += "操作";
        }
        logBean.setLogType(type);
        logBean.setLogIpAddress(ip);
        logBean.setLogOperator(realName);
        logBean.setLogContent(desc);
        logBean.setAdminBean(adminBean);
        logService.saveLog(logBean);
        System.out.println(desc + "   ip地址是：" + ip);
    }

    /**
     * 获取ip地址
     * @param request HttpRequest请求对象
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if(("0:0:0:0:0:0:0:1").equals(ip)){
            ip="127.0.0.1";
        }
        return ip;
    }
}

