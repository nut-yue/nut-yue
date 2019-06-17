package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : ExternalEntity
 * @Date ：2019/5/28 13:55
 * @Desc ：类的介绍： 外部合约实体类
 * @author：作者：胡平
 * @change:峗权（externalPrice的类型由String修改为int）
 */
@Data
@TableName("t_external")
public class ExternalEntity {
    /**编号*/
    @TableId(value = "externalId", type = IdType.AUTO)
    private Integer externalId;
    /**合同编号*/
    private String externalNo;
    /**合同单位*/
    private String externalUnit;
    /**对方联系人*/
    private String externalContact;
    /**对方联系人电话*/
    private String externalPhone;
    /**车位所属单位详细地址*/
    private String externalAddress;
    /**合同生效日期*/
    private String externalffectivedate;
    /**合同截止日期*/
    private String externalDeadline;
    /**成交价格*/
    private int externalPrice;
    /**上传合同复印件*/
    private String externalCopy;
    /**合同状态  分为：已生效、未生效、已过期、失效（解约） */
    private String externalContractStatus;
    /**原合同id*/
    private Integer originalId;
    /**平台联系人*/
    private String externaLinkman;
    /**平台联系电话*/
    private String externaLinkmanTel;
}
