package com.project.servlet;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.project.bean.LogBean;
import com.project.service.ILogService;
import com.project.service.impl.LogServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class ShowLogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ILogService logService=new LogServiceImpl();
        String currentPage=req.getParameter("currentPage");
        String pageSize=req.getParameter("pageSize");
        String startDate=req.getParameter("date");
        String endDate=req.getParameter("number");
        Map<String,String> condition=new HashMap<String,String>();
        condition.put("currentPage",currentPage);
        condition.put("pageSize",pageSize);
        condition.put("startDate",startDate);
        condition.put("endDate",endDate);
       PageInfo<LogBean> page=logService.findByCondition(condition);
        Gson gson=new Gson();
        String json=gson.toJson(page);
        resp.getWriter().print(json);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
