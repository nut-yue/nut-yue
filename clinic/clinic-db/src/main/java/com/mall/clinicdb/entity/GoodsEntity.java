package com.mall.clinicdb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName : GoodsEntity
 * @Date ：2019/12/16 19:09
 * @author：nut-yue
 */
@Data
@Builder
@ApiModel(value = "商品对象实体类", description = "与数据库交互的对象")
@TableName("clinic_goods")
public class GoodsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "goodsId", value = "商品id",example = "uuid")
    @TableId("goodsId")
    private String goodsId;
    @NotNull(message = "商品名称不能为空")
    @ApiModelProperty(name = "goodsName", value = "商品名称",example = "1")
    private String goodsName;
    @NotNull(message = "商品价格不能为空")
    @ApiModelProperty(name = "price", value = "价格",example = "100")
    private BigDecimal price;
    @ApiModelProperty(name = "stock", value = "库存",example = "10")
    private String stock;
    @ApiModelProperty(name = "medicineInfo", value = "商品简介",example = "clinic")
    private String info;
    @ApiModelProperty(name = "type", value = "商品类型",example = "1")
    private String type;
    @ApiModelProperty(name = "isSale", value = "是否上架",example = "1")
    private Integer isSale;
    @ApiModelProperty(name = "picUrl", value = "图片链接",example = "xxxx")
    private String picUrl;
    @ApiModelProperty(name = "addTime", value = "创建时间",example = "2019-12-12")
    private String addTime;
    @ApiModelProperty(name = "detail", value = "商品详情",example = "clinic")
    private String detail;
    @ApiModelProperty(name = "updateTime", value = "修改时间",example = "2019-01-01")
    private String updateTime;
    @ApiModelProperty(name = "deleted", value = "逻辑删除",example = "1")
    private Integer deleted;

}
