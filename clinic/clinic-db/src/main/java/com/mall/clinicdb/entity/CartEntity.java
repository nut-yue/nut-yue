package com.mall.clinicdb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车对象
 * @ClassName : ShopCarEntity
 * @Date ：2020/1/19 22:19
 * @author：nut-yue
 */
@Data
@Builder
@ApiModel(value = "购物车对象")
@TableName("clinic_cart")
public class CartEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value="id")
    @ApiModelProperty(name="id",value="购物车id",example = "uuid")
    private String id;
    @ApiModelProperty(name="userId",value = "用户id",example = "uuid")
    private String userId;
    @ApiModelProperty(name="goodsId",value = "商品id",example = "uuid")
    private String goodsId;
    @ApiModelProperty(name="goodsName",value = "商品名称",example = "1")
    private String goodsName;
    @ApiModelProperty(name="quantity",value="商品数量",example = "10")
    private Integer quantity;
    @ApiModelProperty(name="price",value="商品单价",example = "100")
    private BigDecimal price;
    @ApiModelProperty(name="checked",value="是否选中",example = "1")
    private Integer checked;
    @ApiModelProperty(name="picUrl",value="图片路径",example = "xxxxx")
    private String picUrl;
    @ApiModelProperty(name="addTime",value="添加时间",example = "2019-12-12")
    private String addTime;
    @ApiModelProperty(name="updateTime",value="修改时间",example = "2019-12-12")
    private String updateTime;
    @ApiModelProperty(name="deleted",value="逻辑删除",example = "1")
    private Integer deleted;
}
