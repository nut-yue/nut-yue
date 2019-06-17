package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : ReservationEntity
 * @Date ：2019/5/28 14:32
 * @Desc ：类的介绍：预约实体类
 * @author：作者：胡平
 */
@Data
@TableName("t_reservation")
public class ReservationEntity {
    /**编号*/
    /**
     * 杨强增加了注释idType
     */
    @TableId(value = "reservationId",type = IdType.AUTO)
    private Integer reservationId;
    /**预约类型(买或租)*/
    private String reservationType;
    /**是否处理*/
    private String reservationDeal;
    /**处理内容*/
    private String reservationContent;
    /**车位ID*/
    private Integer carportId;
    /**预约者ID*/
    private Integer appointerUserId;
    /**被预约者ID*/
    private Integer appointedUserId;
}
