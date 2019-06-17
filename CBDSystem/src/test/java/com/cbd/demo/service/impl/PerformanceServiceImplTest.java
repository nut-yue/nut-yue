package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.PerformanceBean;
import com.cbd.demo.entity.PerformanceEntity;
import com.cbd.demo.service.IPerformanceService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
/**
 * @ClassName : PerformanceServiceImplTest
 * @Date ：2019/6/02 15:14
 * @Desc ：类的介绍：性能统计测试类
 * @author：张皓
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceServiceImplTest {
@Autowired
private IPerformanceService performanceService;

    @Test
    public void findAllPerformance() {

        IPage<PerformanceEntity> allPerformance = performanceService.findAllPerformance(null, null, null, 1, 2);
        Assert.assertNotNull(allPerformance);

    }

    @Test
    public void getPerformance() {
        PerformanceBean performance = performanceService.getPerformance(1);
        Assert.assertNotNull(performance);
    }

    @Test
    public void addPerformance() {
        PerformanceEntity performanceEntity = new PerformanceEntity();
        performanceEntity.setPerformanceType("登陆方法");
        performanceEntity.setPerformanceTime(60);
        int i = performanceService.addPerformance(performanceEntity);
        Assert.assertNotEquals(0,i);
    }

    @Test
    public void delPerformance() {
        Assert.assertNotNull(performanceService.delPerformance(6));
    }
}