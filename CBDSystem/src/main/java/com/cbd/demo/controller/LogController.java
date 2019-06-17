package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.entity.LogEntity;
import com.cbd.demo.service.ILogService;
import com.cbd.demo.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : LogController
 * @Date ：2019/6/1 18:13
 * @Desc ：类的介绍： 日志的控制器
 * @author：作者：胡平
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private ILogService logService;

    /**
     * 分页查询所有日志
     * @param logType
     * @param logOperator
     * @param logContent
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Introduce(desc = "分页查询所有日志")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseData listLog(String logType, String logOperator,
                                String logContent, int currentPage, int pageSize) {

        IPage<LogEntity> logBeanPage =
                logService.listLog(logType, logOperator, logContent,
                        currentPage, pageSize);

        ResponseData responseData = new ResponseData();
        responseData.getData().put("page", logBeanPage);
        return responseData;
    }
}
