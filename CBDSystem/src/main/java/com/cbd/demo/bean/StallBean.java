package com.cbd.demo.bean;

import lombok.Data;

/**
 * @ClassName : StallBean
 * @Date ：2019/6/6 10:43
 * @Desc ：类的介绍： 车位的实体bean
 * @author：作者：胡平
 */
@Data
public class StallBean {
    //地址
    private String address;
    //区域名
    private String areaName;
    //起始编号
    private int startNumber;
    //结束编号
    private int endNumber;
    //图片地址
    private String imageAddress;
}
