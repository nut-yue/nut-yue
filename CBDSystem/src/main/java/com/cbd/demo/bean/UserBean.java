package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : UserBean
 * @Date ：2019/5/28 14:11
 * @Desc ：类的介绍：个人用户实体Bean
 * @author：作者：王佳伟
 */
@Data
public class UserBean {
    /**个人用户编号*/
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
    /**信誉值*/
    private int reputation;
    /**用户表*/
    private AdminBean adminBean;

}
