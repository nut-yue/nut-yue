package com.cbd.demo.bean;

import com.cbd.demo.entity.TenantryEntity;
import lombok.Data;

/**
 * @ClassName : companyBillBean
 * @Date ：2019/5/30 18:49
 * @Desc ：类的介绍：
 * @author：作者：刘划轩
 */
@Data
public class CompanyBillBean {
    /**合同编号*/
    private String tenantryContractNum;
    /**成交价格*/
    private int tenantryDealPrice;
    /**合同生效日期*/
    private String tenantryStartTime;
    /**合同截止日期*/
    private String tenantryEndTime;
    /**合同状态  分为：已生效、未生效、已过期、失效（解约） */
    private String tenantryContractStatus;
}
