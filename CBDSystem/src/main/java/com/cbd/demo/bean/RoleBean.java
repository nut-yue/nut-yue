package com.cbd.demo.bean;

import lombok.Data;

import java.util.List;

/**
 * @ClassName : RoleBean
 * @Date ：2019/5/28 14:41
 * @Desc ：类的介绍：角色实体Bean
 * @author：作者：王佳伟
 * @change：峗权（删除了用户实体bean关联）
 */
@Data
public class RoleBean {
    /**角色id*/
    private Integer roleId;
    /**角色名*/
    private String roleName;
    /**角色中文名*/
    private String name;
    /**权限集合*/
    private List<PowerBean> powerBeansList;
}
