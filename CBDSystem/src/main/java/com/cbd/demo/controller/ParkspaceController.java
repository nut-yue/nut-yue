package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.ExternalBean;
import com.cbd.demo.bean.ParkspaceBean;
import com.cbd.demo.bean.StallBean;
import com.cbd.demo.entity.ParkspaceEntity;
import com.cbd.demo.service.IParkspaceService;
import com.cbd.demo.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName : ParkspaceController
 * @Date ：2019/6/1 22:27
 * @Desc ：类的介绍： cdb车位的控制器 批量添加车位（未写）
 * @author：作者：胡平
 */
@RestController
@RequestMapping("/Parkspace")
public class ParkspaceController {

    @Autowired
    private IParkspaceService parkspaceService;

    private final String STATUS = "空闲";

    /**
     * 批量添加车位
     *
     * @param stall
     * @return
     */
    @Introduce(desc = "批量添加车位")
    @RequestMapping(value = "/saveParkspaces", method = RequestMethod.POST)
    public ResponseData saveExternalAndParkspaces(StallBean stall) {
        //cbd的车位bena集合
        List<ParkspaceBean> parkspaceBeans = new ArrayList<ParkspaceBean>();
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
        System.out.println(parkspaceBeans);
        int i = parkspaceService.saveParkspace(parkspaceBeans);
        if (i > 0) {
            return new ResponseData();
        }
        return ResponseData.unauthorized();
    }


    /**
     * 查询单个车位信息
     *
     * @param parkspaceId
     * @return
     */
    @Introduce(desc = "查询单个车位信息")
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseData findById(int parkspaceId) {
        ParkspaceBean parkspace = parkspaceService.getParkspace(parkspaceId);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("parkspace", parkspace);
        return responseData;
    }


    /**
     * 根据合同ID查询企业的所有合同
     *
     * @param tenantryId  合同ID
     * @param currentPage 当前页
     * @param pageSize    每页显示条数
     * @return
     */

    @Introduce(desc = "根据合同ID查询企业的所有合同")
    @RequestMapping(value = "listById", method = RequestMethod.GET)
    public ResponseData listTenantryId(int tenantryId, int currentPage, int pageSize) {
        IPage<ParkspaceEntity> page =
                parkspaceService.listTenantryId(tenantryId, currentPage, pageSize);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("parkspaces", page);
        return responseData;
    }

    /**
     * 租户合约，解约
     *
     * @param tenantryId
     */
    @Introduce(desc = "租户合约，解约")
    @RequestMapping(value = "updateCart", method = RequestMethod.PUT)
    public ResponseData updateByTenantryId(int tenantryId) {
        parkspaceService.updateByTenantryId(tenantryId);
        return new ResponseData();
    }

    /**
     * 修改cdb车位状态
     *
     * @param tenantryId      租户合约的id
     * @param parkspaceStatus 状态
     * @return
     */
    @Introduce(desc = "根据租户合约的id，修改cbd车位状态")
    @RequestMapping(value = "updateStatus", method = RequestMethod.PUT)
    public ResponseData updateByTenantryId(int tenantryId, String parkspaceStatus) {
        parkspaceService.updateByTenantryId(tenantryId, parkspaceStatus);
        return new ResponseData();
    }

    /**
     * 查询所有cbd，可以动态条件
     *
     * @param currentPage      当前页
     * @param pageSize         每页显示条数
     * @param parkspaceStatus  状态
     * @param parkspaceAddress 地址
     * @return
     */
    @Introduce(desc = "分页查询cbd的车位")
    @RequestMapping(value = "all1", method = RequestMethod.GET)
    public ResponseData listParkspace(int currentPage, int pageSize, String parkspaceStatus,
                                      String parkspaceAddress) {
        IPage<ParkspaceEntity> page =
                parkspaceService.listParkspace(currentPage, pageSize, parkspaceStatus, parkspaceAddress);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("parkspace", page);
        return responseData;
    }

    /**
     * @param parkspaceStatus
     * @param endDate
     * @param tenantryId
     * @return
     */
    @Introduce(desc = "查询cdb所有车位")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseData listParkspace(String parkspaceStatus, String endDate, String tenantryId) {
        System.out.println(parkspaceStatus + "" + endDate + "" + tenantryId);
        List<ParkspaceEntity> list =
                parkspaceService.listLeisureParkspacce(parkspaceStatus, endDate, tenantryId);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("parkspace", list);
        return responseData;
    }

    /**
     * 通过id批量修改
     *
     * @param parkspaceIdStr  传递参数的格式 （1,2,3,4)注意必须这种格式,我在controller中通过逗号分隔的
     * @param parkspaceStatus
     * @return
     */
    @Introduce(desc = "通过id批量修改")
    @RequestMapping(value = "/updateParkspaces", method = RequestMethod.PUT)
    public ResponseData updateParkspace(String parkspaceIdStr, String parkspaceStatus) {
        Integer[] parkspaceIds = stringArrTurnIntArr(parkspaceIdStr);
        ResponseData responseData = null;
        if (parkspaceIds.length > 0) {
            List<Integer> ids = Arrays.asList(parkspaceIds);
            parkspaceService.updateParkspace(ids, parkspaceStatus);
            responseData = new ResponseData();
            responseData.setMessage("修改成功");
            return responseData;
        }
        responseData = new ResponseData();
        responseData.setMessage("参数不对");
        responseData.setCode(404);
        return responseData;
    }

    /**
     * String数组转为int数组
     *
     * @param str
     * @return
     */
    private Integer[] stringArrTurnIntArr(String str) {
        String[] strs = str.split(",");
        Integer[] ints = new Integer[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ints[i] = Integer.parseInt(strs[i].trim());
        }
        return ints;
    }
}
