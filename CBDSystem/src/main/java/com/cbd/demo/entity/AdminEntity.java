package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : AdminEntity
 * @Date ：2019/5/28 13:48
 * @Desc ：类的介绍： 用户实体类
 * @author：作者：王佳伟
 * @change：峗权（增加了角色外键关联）
 */
@Data
@TableName("t_admin")
public class AdminEntity {
    /**用户编号*/
    @TableId(value = "adminId",type = IdType.AUTO)
    private Integer adminId;
    /**用户名*/
    private String adminName;
    /**密码*/
    private String adminPassword;
    /**角色类型 1是个人表，2是企业表，3是管理员表*/
    private int adminRoleType;
    /**角色ID*/
    private int roleId;
}
