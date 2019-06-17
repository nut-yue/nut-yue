package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : PeopleNumEntity
 * @Date ：2019/5/28 14:12
 * @Desc ：类的介绍： 在线人数统计表
 * @author：作者：胡平
 */
@Data
@TableName("t_peopleNum")
public class PeopleNumEntity {
    /**编号*/
    @TableId(value = "peopleNumId",type = IdType.AUTO)
    private Integer peopleNumId;
     /**日期*/
     private String peopleNumDate;
     /**时间点*/
     private String peopleNumTime;
     /**在线人数*/
     private int peopleNums;

}
