package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : CarportBean
 * @Date ：2019/5/28 14:27
 * @Desc ：类的介绍：个人车位Bean
 * @author：作者：胡平
 * @change 峗权
 */
@Data
public class CarportBean {
    /**编号*/
    private Integer carportId;
    /**车位地址*/
    private String carportAddress;
    /**  车位编号*/
    private String carportNumber;
    /** 产权证编号*/
    private String carportProperty;
    /** 车位产权复印件*/
    private String carportPropertyDoc;
    /**  车位状态 分为 审核中、发布中、空闲、已租赁、已卖出、已预约、发布出售信息中，发布出租信息中*/
    private String carportStatus;
    /** 车位照片（修改后添加的字段）*/
    private String carportImage;
    /**用户id*/
    private UserBean  UserBean;
    /**出租信息对象*/
    private RentBean rentBean;
    /**出售信息对象*/
    private SaleBean saleBean;
}
