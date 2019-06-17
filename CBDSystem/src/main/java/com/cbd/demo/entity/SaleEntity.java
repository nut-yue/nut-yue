package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : SaleEntity
 * @Date ：2019/5/28 14:50
 * @Desc ：类的介绍： 出售信息表
 * @author：作者：胡平
 */
@Data
@TableName("t_sale")
public class SaleEntity {
    /**编号*/
    @TableId(value = "saleId",type = IdType.AUTO)
    private Integer saleId;
    /**价格*/
    private int salePrice;
    /**车位信息ID*/
    private Integer carportId;
}
