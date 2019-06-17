package com.cbd.demo.bean;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : CompanyBean
 * @Date ：2019/5/28 14:28
 * @Desc ：类的介绍：企业用户实体Bean
 * @author：作者：王佳伟
 */
@Data
public class CompanyBean {
    /**企业用户编号*/
    private Integer companyId;
    /**企业名称*/
    private String companyName;
    /**企业楼层位置*/
    private String companyPosition;
    /**企业联系人*/
    private String companyContact;
    /**企业联系电话*/
    private String companyPhone;
    /**用户名*/
    private AdminBean adminBean;
}
