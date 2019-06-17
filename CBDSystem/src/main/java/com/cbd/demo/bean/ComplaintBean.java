package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : ComplaintBean
 * @Date ：2019/5/28 14:31
 * @Desc ：类的介绍： 投诉实体Bean
 * @author：岳超
 * @change：峗权(增加了个人账单bean)
 */
@Data
public class ComplaintBean{
    /**投诉id*/
    private Integer complaintId;
    /**投诉状态（审核中，已驳回，已处理）*/
    private String complaintStatus;
    /**投诉描述*/
    private String complaintDescription;
    /**投诉类型（租车投诉、买车投诉）*/
    private String complaintTypes;
    /**投诉时间*/
    private String complaintDate;
    /**投诉人*/
    private UserBean userBean;
    /**被投诉人*/
    private UserBean respondentUserBean;
    /**个人账单*/
    private BillBean billBean;

}