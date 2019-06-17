package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.PerformanceBean;
import com.cbd.demo.entity.PerformanceEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : IPerformanceDao
 * @Date ：2019/5/31 10:24
 * @Desc ：接口的介绍：性能的dao接口
 * @author：作者：张皓
 */
public interface IPerformanceDao {
    /**
     * 根据条件查询所有性能统计信息,没传值为查询所有
     * @param queryWrapper 条件构造器
     * @return 性能集合
     */
    IPage<PerformanceEntity> findAllPerformance(IPage<PerformanceEntity> page, Wrapper<PerformanceEntity> queryWrapper) throws Exception;

    /**
     * 根据性能信息ID查询具体性能
     * @param performanceId 性能信息ID
     * @return 性能实体bean
     */
    PerformanceEntity findByPerformanceId(Serializable performanceId) throws Exception;
    /**
     * 增加性能统计信息
     * @param performanceEntity 性能统计实体类
     * @return 成功1，失败0
     */
    int addPerformance(PerformanceEntity performanceEntity) throws Exception;

    /**
     * 根据性能信息ID删除出售信息
     * @param performanceId 性能信息ID
     * @return 成功1，失败0
     */
    int delPerformance(Serializable performanceId) throws Exception;

    /**
     * 动态条件查询性能统计集合
     * @param performanceDate 条件
     * @return 性能统计集合
     */
    List<PerformanceEntity> listPerformance(String performanceDate);
}
