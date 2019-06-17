package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : PersonalEntity
 * @Date ：2019/5/28 13:31
 * @Desc ：类的介绍： 个人买卖车合同实体类
 * @author :作者：胡平
 */
@Data
@TableName("t_personal")
public class PersonalEntity {
    /**编号*/
    @TableId(value = "personalId",type = IdType.AUTO)
    private Integer personalId;
    /**合同编号*/
    private String personalContractNum;
    /**成交价格*/
    private int personalPrice;
    /**生效日期*/
    private String personalDate;
    /**买方是否签约 0 未签约、 1签约*/
    private String personalBuyIsSigning;
    /**卖方是否签约 0 未签约、 1 签约*/
    private String personalSellIsSigning;
    /**车位出售方ID*/
    private Integer sellUserId;
    /**车位购买方ID*/
    private Integer buyUserId;
    /**车位ID*/
    private Integer carportId;
    /**计费账单ID*/
    private Integer billId;

}
