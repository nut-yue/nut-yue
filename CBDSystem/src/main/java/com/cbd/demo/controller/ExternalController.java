package com.cbd.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.ExternalBean;
import com.cbd.demo.bean.ParkspaceBean;
import com.cbd.demo.bean.StallBean;
import com.cbd.demo.entity.ExternalEntity;
import com.cbd.demo.service.IExternalService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : ExternalController
 * @Date ：2019/6/2 15:05
 * @Desc ：类的介绍：外部合约页面控制器
 * @author：作者：峗权
 */
@RestController
@RequestMapping(value = "/external")
public class ExternalController {
    @Autowired
    private IExternalService externalService;


    private final String STATUS = "空闲";
    /**
     * 添加合同，批量添加车位
     *
     * @return
     */
    @RequestMapping(value = "/saveExternalAndParkspaces", method = RequestMethod.POST)
    public ResponseData saveExternalAndParkspaces(@RequestBody ExternalBean externalBean) {
        System.out.println(externalBean);
        //批量添加的车位
        List<StallBean> stallBeans = externalBean.getStallBeans();

        //cbd的车位bena集合
        List<ParkspaceBean> parkspaceBeans = new ArrayList<ParkspaceBean>();

        for (StallBean stall : stallBeans) {
            //遍历得到车位的详细
            for (int i = stall.getStartNumber(); i <= stall.getEndNumber(); i++) {
                ParkspaceBean parkspace = new ParkspaceBean();
                //设置地址
                parkspace.setParkspaceAddress(stall.getAddress());
                //设置区域编号
                parkspace.setParkspaceRegionNumber(stall.getAreaName());
                //设置车位编号
                parkspace.setParkspaceNumber(stall.getAreaName() + i + "号");
                //设置车位状态 parkspaceStatus
                parkspace.setParkspaceStatus(STATUS);
                //设置图片
                parkspace.setParkspaceImage(stall.getImageAddress());
                //设置车位的集合
                parkspaceBeans.add(parkspace);
            }
        }
        int num = externalService.saveExternal(externalBean, parkspaceBeans);
        if (num != 0) {
            ResponseData responseData = new ResponseData();
            responseData.getData().put("num", num);
            return responseData;
        }
        return ResponseData.unauthorized();
    }


    /**
     * 添加合同
     *
     * @param externalBean   外部合约的实体类
     * @param parkspaceBeans 车位的list集合
     * @return
     */
    @Introduce(desc = "添加合同")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseData saveExternal(ExternalBean externalBean, String parkspaceBeans) {
        String[] str = parkspaceBeans.split(",");

        List<ParkspaceBean> parkspaceBeanList = new ArrayList<ParkspaceBean>();
        for (int i = 0; i < str.length; i++) {
            str[i] = str[i].trim();
            ParkspaceBean parkspaceBean = new ParkspaceBean();
            parkspaceBean.setExternalBean(externalBean);
            parkspaceBean.setTenantryBean(null);
            parkspaceBean.setParkspaceNumber(str[i]);
            parkspaceBean.setParkspaceStatus("已租赁");

            parkspaceBeanList.add(parkspaceBean);
        }

        int num = externalService.saveExternal(externalBean, parkspaceBeanList);
        if (num != 0) {
            ResponseData responseData = new ResponseData();
            responseData.getData().put("num", num);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 合同的续约
     *
     * @param externalBean 新的外部合约的实体类
     * @return
     */
    @Introduce(desc = "合同的续约")
    @RequestMapping(value = "/contract", method = RequestMethod.POST)
    public ResponseData contractExternal(@RequestBody ExternalBean externalBean) {
        int num = externalService.contractExternal(externalBean);
        if (num != 0) {
            ResponseData responseData = new ResponseData();
            responseData.getData().put("num", num);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 合同的解约
     *
     * @param externalBean 解约的合同对象
     * @return
     */
    @Introduce(desc = "合同的解约")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseData cancelExternal(@RequestBody ExternalBean externalBean) {
        System.out.println(externalBean);
        int num = externalService.cancelExternal(externalBean);
        if (num != 0) {
            ResponseData responseData = new ResponseData();
            responseData.getData().put("num", num);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 分页查询外部合同
     *
     * @param currentPage            当前页面
     * @param pageSize               每页显示条数
     * @param startDate              开始时间
     * @param endStartDate           结束时间
     * @param externalContractStatus 状态
     * @return
     */
    @Introduce(desc = "分页查询外部合同")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData listExternal(int currentPage, int pageSize,
                                     String startDate, String endStartDate,
                                     String externalContractStatus) {
        IPage<ExternalEntity> page = externalService.listExternal(currentPage
                , pageSize, startDate, endStartDate, externalContractStatus);
        if (page != null) {
            ResponseData responseData = new ResponseData();
            responseData.getData().put("page", page);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 获取单个ExternalBean对象
     *
     * @param id 外部合约的externalId
     * @return
     */
    @Introduce(desc = "获取单个ExternalBean对象")
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public ResponseData getExternalByExternalId(int id) {
        ExternalBean externalBean = externalService.getExternalByExternalId(id);
        if (externalBean != null) {
            ResponseData responseData = new ResponseData();
            responseData.getData().put("external", externalBean);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

}
