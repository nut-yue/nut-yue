package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.PerformanceBean;
import com.cbd.demo.entity.PerformanceEntity;
import com.cbd.demo.service.IPerformanceService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : PerformanceController
 * @Date ：2019/6/2/0002 20:07
 * @Desc ：类的介绍：性能统计controller
 * @author：张皓
 */
@RestController
public class PerformanceController {
    @Autowired
    private IPerformanceService performanceService;

    @RequestMapping(value = "/performance/num",method = RequestMethod.GET)
    public ResponseData getPerformance(){
        ResponseData responseData = new ResponseData();
        Map<String, List<Integer>> map = performanceService.sumPerformance();
        responseData.getData().put("map",map);
        return responseData;
    }
    @RequestMapping(value = "/user/findAllPerformance",method = RequestMethod.GET)
    public ResponseData findAllPerformance(String performanceType, String gtPerformanceTime,String ltPerformanceTime, int currentPage, int pageSize){
        ResponseData responseData = new ResponseData();
        IPage<PerformanceEntity> allPerformance = performanceService.findAllPerformance(performanceType, gtPerformanceTime, ltPerformanceTime, currentPage, pageSize);
        if (allPerformance!=null){
            responseData.getData().put("findAllPerformance",allPerformance);
            return responseData;
        }
        responseData.getData().put("findAllPerformance",0);
        return responseData;
    }

    @RequestMapping(value = "/user/getPerformance",method = RequestMethod.GET)
    public ResponseData getPerformance(int performanceId){
        ResponseData responseData = new ResponseData();
        PerformanceBean performance = performanceService.getPerformance(performanceId);
        if (performance!=null){
            responseData.getData().put("getPerformance",performance);
            return responseData;
        }
        responseData.getData().put("getPerformance",0);
        return responseData;
    }
    @RequestMapping(value = "/user/addPerformance",method = RequestMethod.GET)
    public ResponseData addPerformance(PerformanceEntity performanceEntity){
        ResponseData responseData = new ResponseData();
        int i = performanceService.addPerformance(performanceEntity);
        if (i>0){
            responseData.getData().put("addPerformance",i);
            return responseData;
        }
        responseData.getData().put("addPerformance",0);
        return responseData;
    }
    @RequestMapping(value = "/user/delPerformance",method = RequestMethod.GET)
    public ResponseData delPerformance(int performanceId){
        ResponseData responseData = new ResponseData();
        int i = performanceService.delPerformance(performanceId);
        if (i>0){
            responseData.getData().put("delPerformance",i);
            return responseData;
        }
        responseData.getData().put("delPerformance",0);
        return responseData;
    }

}
