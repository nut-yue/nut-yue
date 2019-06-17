package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : AdministratorEntity
 * @Date ：2019/5/28 14:25
 * @Desc ：类的介绍： 管理员实体类
 * @author：作者：王佳伟
 * @change 峗权（修改了设置id自增）
 */
@Data
@TableName("t_administrator")
public class AdministratorEntity {
    /**管理员编号*/
     @TableId(value = "administratorId",type = IdType.AUTO)
    private  Integer administratorId;
    /**管理员姓名*/
    private String administratorName;
    /**管理员电话*/
    private String administratorPhone;
    //用户外键
    private Integer adminId;
}
