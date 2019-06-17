package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : PeopleNumBean
 * @Date ：2019/5/28 14:12
 * @Desc ：类的介绍： 在线人数统计Bean
 * @author：作者：胡平
 */
@Data
public class PeopleNumBean {
    /**编号*/
    private Integer peopleNumId;
     /**日期*/
     private String peopleNumDate;
     /**时间点*/
     private String peopleNumTime;
     /**在线人数*/
     private int peopleNums;

}
