package com.cbd.demo.bean;

import com.cbd.demo.entity.RoleEntity;
import lombok.Data;

/**
 * @ClassName : AdminBean
 * @Date ：2019/5/28 13:48
 * @Desc ：类的介绍： 用户实体bean
 * @author：作者：王佳伟
 * @change：峗权（增加了角色实体Bean关联）
 */
@Data
public class AdminBean {
    /**用户编号*/
    private Integer adminId;
    /**用户名*/
    private String adminName;
    /**密码*/
    private String adminPassword;
    /**角色类型 1是个人表，2是企业表，3是管理员表*/
    private int adminRoleType;
    /**角色实体类*/
    private RoleEntity roleBean;


}
