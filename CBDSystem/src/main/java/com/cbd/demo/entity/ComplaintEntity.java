package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : Complaint
 * @Date ：2019/5/28 14:31
 * @Desc ：类的介绍： 投诉实体类
 * @author：岳超
 * @change：峗权（增加了个人账单外键）
 */
@Data
@TableName("t_complaint")
public class ComplaintEntity {

    /**投诉id*/
    @TableId(value = "complaintId",type = IdType.AUTO)
    private Integer complaintId;
    /**投诉状态（审核中，已驳回，已处理）*/
    private String complaintStatus;
    /**投诉描述*/
    private String complaintDescription;
    /**投诉类型（租车投诉、买车投诉）*/
    private String complaintTypes;
    /**投诉时间*/
    private String complaintDate;
    /**投诉人id*/
    private Integer userId;
    /**被投诉人id*/
    private Integer respondentUserId;
    /**账单id*/
    private Integer billId;

}
