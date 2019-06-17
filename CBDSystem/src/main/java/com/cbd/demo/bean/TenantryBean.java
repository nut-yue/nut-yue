package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : TenantryEntity
 * @Date ：2019/5/28 14:05
 * @Desc ：类的介绍： 租户合约实体Bean
 * @author：作者：胡平
 */
@Data
public class TenantryBean {
    /**编号*/
    private Integer tenantryId;
    /**合同编号*/
    private String tenantryContractNum;
    /**成交价格*/
    private int tenantryDealPrice;
    /**企业用户名称*/
    private String tenantryUserName;
    /**合同生效日期*/
    private String tenantryStartTime;
    /**合同截止日期*/
    private String tenantryEndTime;
    /**对方联系人*/
    private String tenantryLinkman;
    /**对方联系电话*/
    private String tenantryLinkmanTel;
    /**上传新合同复印件*/
    private String tenantryProfession;
    /**合同状态  分为：已生效、未生效、已过期、失效（解约） */
    private String tenantryContractStatus;
    /**企业联系人*/
    private String tenantryContact;
    /**企业联系人电话*/
    private String tenantryPhone;
    /**企业id*/
    private CompanyBean companyBean;
    /**原合同id*/
    private Integer tenantryOriginalId;
    /**cbd车位对象*/
    private ParkspaceBean parkspaceBean;
}
