//package com.mall.clinicapi.jwt;
//
//import cn.hutool.http.HttpStatus;
//import com.mall.clinicutil.exception.UnauthorizedException;
//import com.sun.org.apache.regexp.internal.RE;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//@Slf4j
//public class JwtFilter extends BasicHttpAuthenticationFilter {
//
//    /**
//     * 对跨域提供支持
//     */
//    @Override
//    protected boolean preHandle(ServletRequest request,ServletResponse response) throws Exception {
//        System.out.println("执行顺序：1");
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//        // 跨域时会首先发送一个option请求，这里给option请求返回200
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpServletResponse.setStatus(HttpStatus.HTTP_OK);
//            return false;
//        }
//        return super.preHandle(request,response);
//    }
//
//    /**
//     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
//     * 例如我们提供一个地址 GET /article
//     * 登入用户和游客看到的内容是不同的
//     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
//     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
//     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
//     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
//     */
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request,ServletResponse response,Object mappedValue) {
//        System.out.println("执行顺序：2");
//        if (isLoginAttempt(request,response)) {
//            try {
//                return executeLogin(request,response);
//            } catch (Exception e) {
//                log.warn(e.getMessage());
//                System.out.println("捕获到异常");
//                return false;
//            }
//        }
//        return false;
//    }
//
//
//
//    /**
//     * 判断用户是否要登入,检测Header里是否含有Authorization字段
//     */
//    @Override
//    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
//        System.out.println("执行顺序：3");
//        HttpServletRequest req = (HttpServletRequest) request;
//        String authorization = req.getHeader("Authorization");
//        return authorization != null;
//    }
//
//    @Override
//    protected boolean executeLogin(ServletRequest request,ServletResponse response) throws IOException {
//        System.out.println("执行顺序：4");
//        try {
//            HttpServletRequest req = (HttpServletRequest) request;
//            String authorization = req.getHeader("Authorization");
//            // 创建JwtToken
//            JwtToken token = new JwtToken(authorization);
//            // 提交给自定义Realm进行认证
//            getSubject(request,response).login(token);
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            System.out.println("捕获到异常啦啦啦啦");
//            return false;
//        }
//        // 如果没有抛出异常则认证通过，返回true
//        return true;
//    }
//
//
//    private void responseStatus(ServletRequest request, ServletResponse response) {
//        try {
//            HttpServletResponse resp = (HttpServletResponse) response;
//            resp.sendRedirect("/login/login.html");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
