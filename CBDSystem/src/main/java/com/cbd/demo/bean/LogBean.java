package com.cbd.demo.bean;

import lombok.Data;

/**
 * @ClassName : LogBean
 * @Date ：2019/5/28 14:34
 * @Desc ：类的介绍：日志实体Bean
 * @author：作者：王佳伟
 */
@Data
public class LogBean {
    /**日志编号*/
    private Integer logId;
    /**创建时间*/
    private String logDate;
    /**ip地址*/
    private String logIpAddress;
    /**操作人*/
    private String logOperator;
    /**日志内容*/
    private String logContent;
    /**日志类型：前台登录、前台操作、后台登录、后台操作*/
    private String logType;
    /**用户实体类*/
    private AdminBean adminBean;
}
