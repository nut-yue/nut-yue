package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : ReservationBean
 * @Date ：2019/5/28 14:32
 * @Desc ：类的介绍：预约实体Bean
 * @author：作者：胡平
 */
@Data
public class ReservationBean {
    /**编号*/
    private Integer reservationId;
    /**预约类型(买或租)*/
    private String reservationType;
    /**是否处理*/
    private String reservationDeal;
    /**处理内容*/
    private String reservationContent;
    /**车位*/
    private CarportBean carportBean;
    /**预约者ID*/
    private UserBean appointerUserBean;
    /**被预约者ID*/
    private UserBean appointedUserBean;
}
