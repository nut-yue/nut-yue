package com.cbd.demo.controller;

import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.entity.PowerEntity;
import com.cbd.demo.service.IPowerService;
import com.cbd.demo.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName : PowerControllre
 * @Date ：2019/6/3 13:10
 * @Desc ：
 * @author：作者：lvyong
 */
@RestController
@RequestMapping("/power")

public class PowerControllre {
    @Autowired
    private IPowerService iPowerService;
    @Introduce(desc ="查看权限")
    @RequestMapping(value = "look",method = RequestMethod.GET)
    public ResponseData look(int reoleId) throws Exception {
        ResponseData responseData = new ResponseData();
       List<PowerEntity> list=iPowerService.listPower( reoleId);
       if(list!=null){
           responseData.getData().put("get",list);
           return responseData;
       }
       return ResponseData.notFound();
    }
}
