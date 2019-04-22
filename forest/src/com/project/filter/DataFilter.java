package com.project.filter;

import com.project.bean.UserBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter(filterName = "dataFilter",value = {"/dataManagement/pages/addInsect.html","/dataManagement/pages/addiInsectDisease.html","/dataManagement/pages/addMouse.html"})
public class DataFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        UserBean userBean = (UserBean) session.getAttribute(session.getId());
        if (userBean.getUserScaleId() == 0||userBean.getUserScaleId()==1) {
            System.out.println("进入资料过滤");
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            request.getRequestDispatcher("../../authority/system.html").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
