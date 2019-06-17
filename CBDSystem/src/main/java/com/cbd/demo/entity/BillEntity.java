package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : BillEntity
 * @Date ：2019/5/28 14:24
 * @Desc ：类的介绍： 计费账单实体类
 * @author：岳超
 */
@Data
@TableName("t_bill")
public class BillEntity {

    /**账单id*/
    @TableId(value = "billId",type = IdType.AUTO)
    private Integer billId;
    /**交易日期*/
    private String billDate;
    /**交易时间*/
    private String billTime;
    /**金额*/
    private int billMoney;
    /**账单类型（个人租车账单、个人购车账单）*/
    private String billType;
    /**备注*/
    private String billRemark;
    /**所属车位*/
    private Integer carportId;
    /**甲方id*/
    private Integer partlyAId;
    /**乙方id*/
    private Integer partlyBId;
}
