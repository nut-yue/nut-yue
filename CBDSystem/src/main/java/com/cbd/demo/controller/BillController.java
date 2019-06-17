package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.BillBean;
import com.cbd.demo.bean.DealCountBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.entity.BillEntity;
import com.cbd.demo.service.IBillService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：杨强
 * @date ：Created in 2019/6/3 0003 9:27
 */
@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private IBillService billService;

    /**
     * 查询所有方法
     * @param currentPage 当前页数
     * @param pageSize 每页显示条数
     * @param oldDate 旧时间
     * @param newDate 新时间
     * @param billType 类型
     * @return
     */
    @Introduce(desc="查询所有账单")
    @RequestMapping(value = "getBillAll",method = RequestMethod.GET)
    public ResponseData getBillAll(int currentPage, int pageSize, String oldDate, String newDate, String billType, HttpSession session){
        // 用户对象从session中获取
        UserBean userBean = (UserBean) session.getAttribute("login");
        // 测试手动设置
        int id=userBean.getUserId();
        ResponseData responseData=new ResponseData();
        Map<String,Object>map=new HashMap<>();
        IPage<BillEntity> page=billService.getBillAll(id, currentPage, pageSize, oldDate, newDate, billType);
        map.put("page",page);
        responseData.setData(map);
        return responseData;
    }

    /**
     * 获取交易金额
     * @return
     */
    @Introduce(desc="获取金额")
    @RequestMapping(value = "getMoney",method = RequestMethod.GET)
    public ResponseData getMoney(HttpSession session){
        // 用户对象从session中获取
         UserBean userBean = (UserBean) session.getAttribute("login");
        // 测试手动设置
        Integer id=userBean.getUserId();
        DealCountBean dealCountBean=billService.getMoney(id);
        Map<String,Object>map=new HashMap<>();
        map.put("result",dealCountBean);
        ResponseData responseData=new ResponseData();
        responseData.setData(map);
        return responseData;
    }

    /**
     * 根据id 查询详情
     * @param id 交易记录id
     * @return
     */
    @Introduce(desc="根据id查询详情")
    @RequestMapping(value = "getBill",method = RequestMethod.GET)
    public ResponseData getBill(Integer id){
        BillBean billBean=billService.getBill(id);
        Map<String,Object>map=new HashMap<>();
        map.put("result",billBean);
        ResponseData responseData=new ResponseData();
        responseData.setData(map);
        return responseData;
    }
}
