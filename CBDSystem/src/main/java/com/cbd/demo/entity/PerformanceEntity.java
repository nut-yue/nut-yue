package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : PerformanceEntity
 * @Date ：2019/5/28 14:19
 * @Desc ：类的介绍： 性能实体类
 * @author：作者：胡平
 */
@Data
@TableName("t_performance")
public class PerformanceEntity {
    /**编号*/
    @TableId(value = "performanceId",type = IdType.AUTO)
    private Integer performanceId;
    /**操作名称*/
    private String performanceType;
    /**响应时间*/
    private int performanceTime;
    /**日期*/
    private String performanceDate;

}
