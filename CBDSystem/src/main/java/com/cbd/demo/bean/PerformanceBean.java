package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : PerformanceBean
 * @Date ：2019/5/28 14:19
 * @Desc ：类的介绍： 性能实体Bean
 * @author：作者：胡平
 */
@Data
public class PerformanceBean {
    /**编号*/
    @TableId
    private Integer performanceId;
    /**操作名称*/
    private String performanceType;
    /**响应时间*/
    private String performanceTime;

}
