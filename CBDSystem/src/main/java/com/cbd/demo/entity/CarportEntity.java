package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : CarportEntity
 * @Date ：2019/5/28 14:27
 * @Desc ：类的介绍：个人车位表
 * @author：作者：胡平
 * @change 峗权
 */
@Data
@TableName("t_carport")
public class CarportEntity {

    /**编号*/
    @TableId(value = "carportId",type = IdType.AUTO)
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
    private Integer userId;
}
