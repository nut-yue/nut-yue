package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbd.demo.entity.BillEntity;
import com.cbd.demo.entity.CarportEntity;
import com.cbd.demo.entity.UserEntity;
import lombok.Data;

/**
 * @ClassName : PersonalBean
 * @Date ：2019/5/28 13:31
 * @Desc ：类的介绍： 个人买卖车合同实体Bean
 * @author :作者：胡平
 */
@Data
public class PersonalBean {
    /**编号*/
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
    private UserEntity sellUserBean;
    /**车位购买方ID*/
    private UserEntity buyUserUserBean;
    /**车位ID*/
    private CarportEntity carportBean;
    /**计费账单ID*/
    private BillEntity billBean;

}
