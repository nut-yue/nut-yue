package com.mall.clinicdb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : UserEntity
 * @Date ：2019/8/27 11:07
 * @author：nut-yue
 */
@Data
@Builder
@ApiModel(value = "用户对象", description = "用户数据库交互的用户对象")
@TableName("clinic_user")
public class UserEntity implements Serializable {

    @ApiModelProperty(name = "userId", value = "用户id",example = "uuid")
    @TableId("userId")
    private String userId;
    @ApiModelProperty(name = "userName", value = "用户名",example = "nut-yue")
    private String userName;
    @ApiModelProperty(name = "password", value = "密码",example = "123")
    private String password;
    @ApiModelProperty(name = "gender", value = "性别",example = "男")
    private String gender;
    @ApiModelProperty(name = "city", value = "城市",example = "成都")
    private String city;
    @ApiModelProperty(name = "province", value = "省份",example = "四川省")
    private String province;
    @ApiModelProperty(name = "address", value = "地址",example = "成都市锦江区")
    private String address;
    @ApiModelProperty(name = "phone", value = "电话",example = "138xxxxxxxx")
    private String phone;
    //扩展字段
    private String extends1;
    private String extends2;
    private String extends3;
}
