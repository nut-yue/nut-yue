package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbd.demo.entity.CarportEntity;
import lombok.Data;

/**
 * @ClassName : BillBean
 * @Date ：2019/5/28 14:24
 * @Desc ：类的介绍： 计费账单Bean
 * @author：岳超
 */
@Data
public class BillBean {
    /**账单id*/
    @TableId
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
    /**甲方*/
    private UserBean partlyA;
    /**乙方*/
    private UserBean partlyB;

    private CarportBean carportBean;
}
