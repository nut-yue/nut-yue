package com.cbd.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : UserEntity
 * @Date ：2019/5/28 14:11
 * @Desc ：类的介绍：个人用户实体类
 * @author：作者：王佳伟
 * @Date:2019/5/31 陈玉婷修改了  @TableId(type = IdType.AUTO)
 */
@Data
@TableName("t_user")
public class UserEntity {
    /**个人用户编号*/
    @TableId(type = IdType.AUTO)
    private Integer userId;
    /**真实姓名*/
    private String userRealName;
    /**家庭住址*/
    private String userAddress;
    /**电话号码*/
    private String userPhone;
    /**身份证号码*/
    private String userIdCard;
    /**职业描述*/
    private String userProfession;
    /**邮箱地址*/
    private String userEmailAddress;
    /**交易次数*/
    private int userDeal;
    /**被投诉次数*/
    private int userComplaint;
    /**用户表外键*/
    private Integer adminId;
}