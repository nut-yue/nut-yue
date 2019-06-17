package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @ClassName : TransactionEntity
 * @Date ：2019/5/28 14:37
 * @Desc ：类的介绍： 出租交易记录表
 * @author：作者：胡平
 */
@Data
@TableName("t_transaction")
public class TransactionEntity {
    /**编号*/
    @TableId(value = "transactionId",type = IdType.AUTO)
    private Integer transactionId;
    /**车位信息ID*/
    private Integer carportId;
    /**租借开始时间*/
    private String transactionTime;
    /**租借结束时间*/
    private String transactionEndTime;
    /**出租价格*/
    private int transactionPrice;
    /**计费账单ID*/
    private Integer billId;
    /**出租者ID*/
    private Integer rentUserId;
    /**租赁者ID*/
    private Integer leaseUserId;
}
