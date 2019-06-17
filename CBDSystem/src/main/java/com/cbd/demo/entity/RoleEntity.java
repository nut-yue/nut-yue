package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @ClassName : RoleEntity
 * @Date ：2019/5/28 14:41
 * @Desc ：类的介绍：角色实体类
 * @author：作者：王佳伟
 * @change：峗权（删除了用户外键关联）
 */
@Data
@TableName("t_role")
public class RoleEntity {
    /**角色id*/
    @TableId(value = "userId",type = IdType.AUTO)
    private Integer roleId;
    /**角色名*/
    private String roleName;
    /**角色中文名*/
    private String name;
    /**权限集合*/
    private List<PowerEntity> power;
}
