package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : ParkspaceEntity
 * @Date ：2019/5/28 14:22
 * @Desc ：类的介绍：CBD车位表实体类
 * @author：作者：胡平
 */
@Data
@TableName("t_parkspace")
public class ParkspaceEntity {
    /**编号*/
    @TableId(value = "parkspaceId", type = IdType.AUTO)
    private Integer parkspaceId;
     /**车位地址*/
    private String parkspaceAddress;
     /**车位区域编号*/
    private String parkspaceRegionNumber;
     /**车位编号*/
    private String parkspaceNumber;
     /**车位状态  分为：空闲、已租赁、  */
    private String parkspaceStatus;
    /**企业车位照片*/
    private String parkspaceImage;
     /**租户合约ID*/
    private Integer tenantryId;
    /**外部合约ID	*/
    private Integer externalId;


}
