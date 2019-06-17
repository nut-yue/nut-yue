package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.PerformanceEntity;
import com.cbd.demo.mapper.PerformanceMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceDaoImplTest {
    @Autowired
    private PerformanceMapper performanceMapper;
    @Test
    public void findAllPerformanceTest(){
        IPage<PerformanceEntity> page= new Page<>(1,2);
        QueryWrapper<PerformanceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("performanceType","登陆");
        IPage<PerformanceEntity> performanceEntityIPage = performanceMapper.selectPage(page, queryWrapper);
        Assert.assertNotNull(performanceEntityIPage);
    }
    @Test
    public void findByPerformanceIdTest(){
        PerformanceEntity performanceEntity = performanceMapper.selectById(1);
        Assert.assertNotNull(performanceEntity);
    }
    @Test
    public void addPerformanceTest(){
        PerformanceEntity performanceEntity = new PerformanceEntity();
        performanceEntity.setPerformanceType("申请");
        performanceEntity.setPerformanceTime(3);
        int insert = performanceMapper.insert(performanceEntity);
        Assert.assertNotEquals(0,insert);
    }
    @Test
    public void delPerformanceTest(){
        int i = performanceMapper.deleteById(3);
        Assert.assertNotEquals(0,i);
    }
}
