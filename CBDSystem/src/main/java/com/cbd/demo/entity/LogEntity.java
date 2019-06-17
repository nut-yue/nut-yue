package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : LogEntity
 * @Date ：2019/5/28 14:34
 * @Desc ：类的介绍：日志实体类
 * @author：作者：王佳伟
 */
@Data
@TableName("t_log")
public class LogEntity {
    /**日志编号*/
    @TableId(value = "logId",type = IdType.AUTO)
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
    private Integer adminId;
}
