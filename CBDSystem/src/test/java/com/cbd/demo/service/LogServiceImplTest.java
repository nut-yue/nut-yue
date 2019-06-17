package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.bean.LogBean;
import com.cbd.demo.entity.LogEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : LogServiceImplTest
 * @Date ：2019/6/1 15:55
 * @Desc ：类的介绍： 日志业务逻辑测试
 * @author：作者：胡平
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogServiceImplTest {

    @Autowired
    private ILogService logService;

    //添加
    @Test
    public void add() {
        LogBean logBean = new LogBean();
        logBean.setLogIpAddress("127.0.0.1");
        logBean.setLogType("前台操作");
        logBean.setLogOperator("张三");
        logBean.setLogContent("登录平台");
        AdminBean adminBean = new AdminBean();
        adminBean.setAdminId(1);
        logBean.setAdminBean(adminBean);
        int i = logService.saveLog(logBean);
        Assert.eq(i, 1, null);
    }


    @Test
    public void listLog() {

        IPage<LogEntity> logBeanIPage =
                logService.listLog("", "",
                "", 2, 10);
        System.out.println(logBeanIPage);
    }
}
