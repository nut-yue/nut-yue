package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : TransactionBean
 * @Date ：2019/5/28 14:37
 * @Desc ：类的介绍： 出租交易记录Bean
 * @author：作者：胡平
 */
@Data
public class TransactionBean {
    /**编号*/
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
    private BillBean billBean;
    /**出租者ID*/
    private UserBean rentUserUserBean;
    /**租赁者ID*/
    private UserBean leaseUserUserBean;
}
