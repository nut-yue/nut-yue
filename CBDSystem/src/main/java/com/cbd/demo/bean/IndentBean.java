package com.cbd.demo.bean;

import lombok.Data;

/**
 * @ClassName : ${NAME}
 * @Date ：2019/5/30 18:36
 * @Desc ：类的介绍：订单详情实体Bean
 * @author：作者：张皓
 */
@Data
public class IndentBean {
    /**购买方*/
    private String buyUserName;
    /**出售方*/
    private String sellUserName;
    /**交易时间*/
    private String dealDate;
    /**车位位置*/
    private String carportAddress;
    /**价格*/
    private int Price;
}
