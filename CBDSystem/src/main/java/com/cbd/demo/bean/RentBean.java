package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : RentBean
 * @Date ：2019/5/28 14:47
 * @Desc ：类的介绍： 出租信息Bean
 * @author：岳超
 */
@Data
public class RentBean {
    /**出租信息id*/
    private Integer rentId;
    /**租借开始时间*/
    private String rentStartDate;
    /**出租结束时间*/
    private String rentEndDate;
    /**出租价格*/
    private int rentPrice;
    /**车位对象*/
    private CarportBean carportBean;
}
