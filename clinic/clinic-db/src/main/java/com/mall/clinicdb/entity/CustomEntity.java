package com.mall.clinicdb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * clinic_custom
 * @author
 */
@Data
@TableName("clinic_custom")
public class CustomEntity implements Serializable {
    /**
     * 用户id
     */
    @TableId("customId")
    private String customId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String gender;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 扩展1
     */
    private String extends1;

    /**
     * 扩展2
     */
    private String extends2;

    /**
     * 扩展3
     */
    private String extends3;

    private static final long serialVersionUID = 1L;
}
