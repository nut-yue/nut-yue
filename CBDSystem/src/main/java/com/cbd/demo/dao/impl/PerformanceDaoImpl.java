package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.PerformanceBean;
import com.cbd.demo.dao.IPerformanceDao;
import com.cbd.demo.entity.PerformanceEntity;
import com.cbd.demo.mapper.PerformanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class PerformanceDaoImpl implements IPerformanceDao {
    @Autowired
    private PerformanceMapper performanceMapper;

    @Override
    public IPage<PerformanceEntity> findAllPerformance(IPage<PerformanceEntity> page, Wrapper<PerformanceEntity> queryWrapper)throws Exception {
        return performanceMapper.selectPage(page,queryWrapper);
    }

    @Override
    public PerformanceEntity findByPerformanceId(Serializable performanceId)throws Exception {
        return performanceMapper.selectById(performanceId);
    }

    @Override
    public int addPerformance(PerformanceEntity performanceEntity)throws Exception {
        return performanceMapper.insert(performanceEntity);
    }

    @Override
    public int delPerformance(Serializable performanceId)throws Exception {
        return performanceMapper.deleteById(performanceId);
    }

    @Override
    public List<PerformanceEntity> listPerformance(String performanceDate) {
        return performanceMapper.avgPerformance(performanceDate);
    }
}
