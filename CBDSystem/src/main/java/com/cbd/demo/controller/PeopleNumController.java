package com.cbd.demo.controller;

import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.service.IPeopleNumService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : PeopleNumController
 * @Date ：2019/6/1 22:17
 * @Desc ：类的介绍： 在线统计人数的controller
 * @author：作者：胡平
 */
@RestController
@RequestMapping("/peopleNum")
public class PeopleNumController {


    @Autowired
    private IPeopleNumService peopleNumService;

    /**
     *  该方法是获取过去一天的在线人数，不能获取当天的在线人数
     * @return
     */
    @Introduce(desc = "在线人数的统计")
    @RequestMapping(value = "/num", method = RequestMethod.GET)
    public ResponseData listPeopleNum() {
        Map<String, List<Integer>> stringListMap = peopleNumService.listPeopleNum();
        ResponseData responseData = new ResponseData();
        responseData.getData().put("map",stringListMap);
        return responseData;
    }

}
