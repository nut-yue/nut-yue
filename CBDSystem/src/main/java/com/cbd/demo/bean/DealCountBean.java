package com.cbd.demo.bean;

import lombok.Data;

/**
 * @ClassName : ${NAME}
 * @Date ：2019/5/30 18:36
 * @Desc ：类的介绍：统计费用实体Bean
 * @author：作者：张皓
 */
@Data
public class DealCountBean {
    /**交易总笔数*/
    private int DealSum;
    /**支出金额*/
    private int payMoney;
    /**收入金额*/
    private int getMoney;
    /**收入总金额*/
    private int getMoneySum;
}
