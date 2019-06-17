package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.internal.bind.v2.model.core.ID;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @ClassName : companyBillEntity
 * @Date ：2019/5/30 18:49
 * @Desc ：类的介绍：企业计费账单Entity
 * @author：作者：王佳伟
 */
@Data
@TableName("t_companybill")
public class CompanyBillEntity {
    /**主键*/
    @TableId(value = "companyBillId",type = IdType.AUTO)
    private Integer companyBillId;
    /**合同id*/
    private Integer pactId;
    /**合同类型*/
    private String companyBillPactType;
    /**金额*/
    private int companyBillMoney;
    /**交易时间*/
    private String companyBillDate;
    /**企业id*/
    private Integer companyId;
}
