package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.SaleBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.service.ISaleService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : SaleController
 * @Date ：2019/6/3/0003 13:35
 * @Desc ：类的介绍：出售信息Controller
 * @author：张皓
 */
@RestController
@RequestMapping("/user")
public class SaleController {
    @Autowired
    private ISaleService saleService;
    @Introduce(desc = "查询所有出售车位信息")
    @RequestMapping(value = "/findAllSaleInfo",method = RequestMethod.GET)
    public ResponseData findAllSaleInfo(String carportAddress, int currentPage, int pageSize, HttpSession session){
        ResponseData responseData = new ResponseData();
        //获取session中的个人用户
        UserBean user= (UserBean) session.getAttribute("login");
        IPage<SaleBean> allSaleInfo = saleService.findAllSaleInfos(user.getUserId(),carportAddress, currentPage, pageSize);
        if (allSaleInfo!=null){
            responseData.getData().put("findAllSaleInfo",allSaleInfo);
            return responseData;
        }
        responseData.getData().put("findAllSaleInfo",0);
        return responseData;
    }
    @Introduce(desc = "查询出售信息详情")
    @RequestMapping(value = "/getSale",method = RequestMethod.GET)
    public ResponseData  getSale(Integer saleId){
        ResponseData responseData = new ResponseData();
        SaleBean sale = saleService.getSale(saleId);
        if (sale!=null){
            responseData.getData().put("getSale",sale);
            return responseData;
        }
        responseData.getData().put("getSale",0);
        return responseData;
    }

    @Introduce(desc = "新增出售信息”")
    @RequestMapping(value = "/addSale",method = RequestMethod.POST)
    public ResponseData  addSale(SaleBean saleBean,int carportId){
        ResponseData responseData = new ResponseData();
        CarportBean carportBean = new CarportBean();
        carportBean.setCarportId(carportId);
        saleBean.setCarportBean(carportBean);
        int i = saleService.addSale(saleBean);
        if (i>0){
            responseData.getData().put("addSale",i);
            return responseData;
        }
        responseData.getData().put("addSale",0);
        return responseData;
    }
}
