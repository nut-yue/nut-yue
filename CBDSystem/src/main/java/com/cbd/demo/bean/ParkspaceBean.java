package com.cbd.demo.bean;

import lombok.Data;

/**
 * @ClassName : ParkspaceBean
 * @Date ：2019/5/28 14:22
 * @Desc ：类的介绍：CBD车位表实体Bean
 * @author：作者：胡平
 */
@Data
public class ParkspaceBean {
    /**编号*/
    private Integer parkspaceId;
     /**车位地址*/
    private String parkspaceAddress;
     /**车位区域编号*/
    private String parkspaceRegionNumber;
     /**车位编号*/
    private String parkspaceNumber;
//     /**产权证编号*/
//    private String parkspacePropertyNumber;
//     /**车位产权复印件*/
//    private String parkspacePropertyDoc;
     /**车位状态  分为：空闲、已租赁、  */
    private String parkspaceStatus;
     /**租户合约*/
    private TenantryBean tenantryBean;
    /**外部合约*/
    private ExternalBean externalBean;
    /**企业车位照片*/
    private String parkspaceImage;
}
