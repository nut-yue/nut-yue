package com.mall.clinicmall_db.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * clinicmall_ad
 * @author
 */
@Data
public class ClinicmallIssue implements Serializable {
    private Integer id;

    /**
     * 广告标题
     */
    private String name;

    /**
     * 所广告的商品页面或者活动页面链接地址
     */
    private String link;

    /**
     * 广告宣传图片
     */
    private String url;

    /**
     * 广告位置：1则是首页
     */
    private Byte position;

    /**
     * 活动内容
     */
    private String content;

    /**
     * 广告开始时间
     */
    private Date startTime;

    /**
     * 广告结束时间
     */
    private Date endTime;

    /**
     * 是否启动
     */
    private Boolean enabled;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    private static final long serialVersionUID = 1L;
}
