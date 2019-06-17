package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.CompanyBean;
import com.cbd.demo.bean.CompanyBillBean;
import com.cbd.demo.bean.DealCountBean;
import com.cbd.demo.entity.CompanyBillEntity;
import com.cbd.demo.service.ICompanyBillService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : CompanyBillServiceImpl
 * @Date ：2019/6/3 10:00
 * @Desc ：企业账单页面监听器
 * @author：作者：刘划轩
 */
@RestController
@RequestMapping("/companyBill")
public class CampanyBillController {
    @Autowired
    private ICompanyBillService companyBillService;

    @Introduce(desc="CBD后台管理员查看所有租户账单")
    @RequestMapping(value = "getCBDBillPage",method = RequestMethod.GET)
    public ResponseData getCBDBillPage(int currentPage, int pageSize, String type, String starTime, String endTime){
        ResponseData responseData = new ResponseData();
        IPage<CompanyBillEntity> page=companyBillService.listCompanyBill(currentPage,pageSize,type,starTime,endTime);
        responseData.getData().put("page",page);
        return responseData;
    }
    @Introduce(desc="企业用户查看所有租户账单")
    @RequestMapping(value = "companyGetBillPage",method = RequestMethod.GET)
    public ResponseData companyGetBillPage(HttpSession session, int currentPage, int pageSize, String type, String starTime, String endTime){
        ResponseData responseData = new ResponseData();
        CompanyBean companyBean=(CompanyBean)session.getAttribute("login");
        IPage<CompanyBillEntity> page=companyBillService.listCompanyBill(companyBean.getCompanyId(),currentPage,pageSize,type,starTime,endTime);
        responseData.getData().put("page",page);
        return responseData;
    }

    @Introduce(desc="通过账单ID查看账单详情")
    @RequestMapping(value = "getBillById",method = RequestMethod.GET)
    public ResponseData getBillById(int id,String billType){
        ResponseData responseData = new ResponseData();
        CompanyBillBean companyBillBean=companyBillService.getCompanyBill(id,billType);
        responseData.getData().put("bill",companyBillBean);
        return responseData;
    }

    @Introduce(desc="通过企业ID统计企业租户账单支出金额、交易笔数")
    @RequestMapping(value = "countMoney",method = RequestMethod.GET)
    public ResponseData countMoney(HttpSession session){
        ResponseData responseData = new ResponseData();
        CompanyBean companyBean=(CompanyBean) session.getAttribute("login");
        DealCountBean dealCountBean=companyBillService.getMoney(companyBean.getCompanyId());
        System.out.println(dealCountBean);
        responseData.getData().put("count",dealCountBean);
        return responseData;
    }

    @Introduce(desc="通过企业ID统计企业租户账单支出金额、交易笔数")
    @RequestMapping(value = "cbdMoney",method = RequestMethod.GET)
    public ResponseData cbdMoney(){
        ResponseData responseData = new ResponseData();
        DealCountBean dealCountBean=companyBillService.getMoney(-1);
        responseData.getData().put("count",dealCountBean);
        return responseData;
    }

}
