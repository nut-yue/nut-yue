package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : RentEntity
 * @Date ：2019/5/28 14:47
 * @Desc ：类的介绍： 出租信息表
 * @author：岳超
 */
@Data
@TableName("t_rent")
public class RentEntity {

    /**出租信息id*/
    @TableId(value = "rentId",type = IdType.AUTO)
    private Integer rentId;
    /**租借开始时间*/
    private String rentStartDate;
    /**出租结束时间*/
    private String rentEndDate;
    /**出租价格*/
    private int rentPrice;
    /**车位id*/
    private int carportId;
}
