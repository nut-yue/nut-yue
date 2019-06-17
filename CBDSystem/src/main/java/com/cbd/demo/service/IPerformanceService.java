package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.PerformanceBean;
import com.cbd.demo.entity.PerformanceEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * @ClassName : IPerformanceService
 * @Date ：2019/5/28 15:34
 * @Desc ：接口的介绍：性能业务逻辑层接口
 * @author：作者：张皓
 */
public interface IPerformanceService {

    /**
     * 根据条件查询所有性能统计信息,没传值为查询所有。
     * 并封装成实体bean装入list集合
     * @param performanceType 操作名称
     * @param gtPerformanceDate 起始日期
     * @param ltPerformanceDate 结束日期
     * @return 性能集合
     */
    IPage<PerformanceEntity> findAllPerformance(String performanceType, String gtPerformanceDate,String ltPerformanceDate, int currentPage, int pageSize);

    /**
     * 根据性能信息ID查询具体性能，并封装成实体bean
     * @param performanceId 性能信息ID
     * @return 性能实体bean
     */
    PerformanceBean getPerformance(int performanceId);
    /**
     * 增加性能统计信息
     * @param performanceEntity 性能统计实体类
     * @return 成功1，失败0
     */
    int addPerformance(PerformanceEntity performanceEntity);

    /**
     * 根据性能信息ID删除性能信息
     * @param performanceId 性能信息ID
     * @return 成功1，失败0
     */
    int delPerformance(int performanceId);

    /**
     * 统计响应时间
     * @return 响应时间集合
     */
    Map<String, List<Integer>> sumPerformance();
}
