package com.cbd.demo.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
/**
 * @ClassName : Company
 * @Date ：2019/5/28 14:28
 * @Desc ：类的介绍：企业用户实体类
 * @author：作者：王佳伟
 */
@Data
@TableName("t_company")
public class CompanyEntity {
    /**企业用户编号*/
    @TableId(value = "companyId",type = IdType.AUTO)
    private Integer companyId;
    /**企业名称*/
    private String companyName;
    /**企业楼层位置*/
    private String companyPosition;
    /**企业联系人*/
    private String companyContact;
    /**企业联系电话*/
    private String companyPhone;
    /**用户名id*/
    private Integer adminId;
}
