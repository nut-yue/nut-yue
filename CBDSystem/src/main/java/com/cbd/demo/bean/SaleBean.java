package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : SaleBean
 * @Date ：2019/5/28 14:50
 * @Desc ：类的介绍： 出售信息Bean
 * @author：作者：胡平
 */
@Data
public class SaleBean {
    /**编号*/
    private Integer saleId;
    /**价格*/
    private int salePrice;
    /**车位信息*/
    private CarportBean carportBean;
    /**购买方*/
    private UserBean userBean;

}
