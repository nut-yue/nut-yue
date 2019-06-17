package com.cbd.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.PerformanceEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName : IPerformanceService
 * @Date ：2019/5/28 15:34
 * @Desc ：接口的介绍：性能的mapper
 * @author：作者：胡平
 */
public interface PerformanceMapper extends SqlMapper,BaseMapper<PerformanceEntity> {
    @Select("select avg(performanceTime) as performanceTime,performanceDate from " +
            "t_performance where performanceDate>=#{performanceDate} group by performanceDate")
    public List<PerformanceEntity> avgPerformance(String performanceDate);
}
