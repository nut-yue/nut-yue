package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.CompanyBean;
import com.cbd.demo.bean.TenantryBean;
import com.cbd.demo.entity.TenantryEntity;
import com.cbd.demo.service.ITenantryService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName : TenantryController
 * @Date ：2019/6/2 17:17
 * @Desc ：类的介绍：租户合约页面控制器
 * @author：岳超
 */
@RestController
@RequestMapping("/tenantry")
public class TenantryController {

    @Autowired
    private ITenantryService tenantryService;

    /**
     * 测试通过(参数说明：ajax传入参数，Bean中属性除原合同id及开始时间外全传,还要传入企业id和cbd车位id)
     * 新建合同
     * @param tenantryBean 租户合同对象
     * @param companyId 企业id
     * @return 响应对象
     */
    @Introduce(desc = "新增租户合同")
    @RequestMapping(value = "addTenantry",method = RequestMethod.POST)
    public ResponseData insertNewTenantry(TenantryBean tenantryBean,int companyId,String ids){
        // 创建企业对象并设置企业id
        CompanyBean companyBean = new CompanyBean();
        companyBean.setCompanyId(companyId);
        // 设置企业对象
        tenantryBean.setCompanyBean(companyBean);
        // 设置原来合同id
        tenantryBean.setTenantryOriginalId(0);
        // 获得车位id集合
        List <Integer> idArray = new ArrayList<>();
        // 解析id集合字符串
        String [] str = ids.split(",");
        // 遍历数组，将数组的值存入集合中
        for (String s : str) {
            idArray.add(Integer.parseInt(s));
        }
        // 获得添加成功条数
        int result = tenantryService.addNewTenantry(tenantryBean,idArray);
        if(result==1){
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过(参数说明：与Bean中除合同生效时间外全部传入,并传入企业id)
     * 续约
     * @param tenantryBean 租户合同对象
     * @param companyId 企业id
     * @return 响应对象
     */
    @Introduce(desc="续约")
    @RequestMapping(value = "renewal",method = RequestMethod.POST)
    public ResponseData contractTenantry(TenantryBean tenantryBean,int companyId,String ids){
        // 创建企业对象并设置企业id
        CompanyBean companyBean = new CompanyBean();
        companyBean.setCompanyId(companyId);
        // 设置企业对象
        tenantryBean.setCompanyBean(companyBean);
        // 解析id集合字符串
        String [] str = ids.split(",");
        // 获得车位id集合
        List <Integer> idArray = new ArrayList<>();
        // 遍历得到车位id并加入集合
        for (String s : str) {
            idArray.add(Integer.parseInt(s));
        }
        // 获得续约成功条数
        int result = tenantryService.contractTenantry(tenantryBean,idArray);
        if(result==1){
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过(参数说明：ajax只需要传入租户合约id即可)
     * 解约
     * @param tenantryId 合约id
     * @return 响应对象v
     */
    @Introduce(desc = "解约")
    @RequestMapping(value="break",method = RequestMethod.PUT)
    public ResponseData updateTenantry(int tenantryId){
        // 获得解约成功条数
        int result = tenantryService.updateTenantry(tenantryId);
        if(result==1){
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过：参数说明(ajax必传参数当前页数和每页显示条数,其余条件为动态查询条件,选填)
     * 根据企业名称动态分页查询合约
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param tenantryUserName 企业名
     * @return 响应对象
     */
    @Introduce(desc = "查询租户合约列表")
    @RequestMapping(value="showAllTenantry",method = RequestMethod.GET)
    public ResponseData showAllTenantry(int currentPage, int pageSize, String startTime, String endTime, String tenantryUserName){
        // 获得分页查询对象
        IPage<TenantryEntity> tenantryPage = tenantryService.showAllTenantry(currentPage, pageSize, startTime, endTime, tenantryUserName);
        if(tenantryPage!=null){
            // 创建响应对象
            ResponseData responseData = new ResponseData();
            // 将分页对象封装到响应对象中
            responseData.getData().put("tenantryPage",tenantryPage);
            // 返货响应对象
            return responseData;
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过：参数说明(ajax传入参数为租户合约id)
     * 查看合约详情
     * @param tenantryId 合约id
     * @return 响应对象
     */
    @Introduce(desc = "查看租户合约详情")
    @RequestMapping(value = "tenantryInfo",method = RequestMethod.GET)
    public ResponseData findById(int tenantryId){
        // 获得查询结果
        TenantryBean tenantryBean = tenantryService.findById(tenantryId);
        if(tenantryBean!=null){
            // 创建响应对象
            ResponseData responseData = new ResponseData();
            // 将查询结果加入响应对象
            responseData.getData().put("tenantry",tenantryBean);
            return responseData;
        }
        return ResponseData.notFound();
    }
}
