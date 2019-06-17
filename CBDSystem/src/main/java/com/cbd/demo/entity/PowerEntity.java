package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.internal.bind.v2.model.core.ID;
import lombok.Data;

/**
 * @ClassName : PowerEntity
 * @Date ：2019/5/28 14:47
 * @Desc ：类的介绍：权限实体类
 * @author：作者：王佳伟
 */
@Data
@TableName("t_power")
public class PowerEntity {
    /**权限id*/
    @TableId(value = "powerId",type = IdType.AUTO)
    private Integer powerId;
    /**权限内容*/
    private String powerContent;
}
