package com.mall.clinicdb.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName : OrderEntity
 * @Date ：2019/8/27 11:04
 * @author：nut-yue
 */
@Data
@Builder
@TableName("clinic-order")
@ApiModel(value = "订单实体类", description = "与数据库交互的实体类")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(name="id",value = "主键id",example = "uuid")
    private String id;
    @ApiModelProperty(name = "orderId", value = "订单id",example = "uuid")
    private String orderId;
    @ApiModelProperty(name = "userId",value = "用户id",example = "uuid")
    private String userId;
    @ApiModelProperty(name="goodsId",value = "商品id",example = "uuid")
    private String goodsId;
    @ApiModelProperty(name = "serialNumber",value = "订单编号",example = "uuid")
    private String serialNumber;
    @ApiModelProperty(name="status",value = "订单状态",example = "1")
    private Integer status;
    @ApiModelProperty(name="consignee",value = "收件人",example = "nut-yue")
    private String consignee;
    @ApiModelProperty(name="mobile",value = "收件人电话",example = "138xxxxxxxx")
    private String mobile;
    @ApiModelProperty(name="address",value = "收货地址",example = "成都市")
    private String address;
    @ApiModelProperty(name="message",value = "留言",example = "clinic")
    private String message;
    @ApiModelProperty(name="goodsPrice",value = "商品价格",example = "100")
    private BigDecimal goodsPrice;
    @ApiModelProperty(name="freightPrice",value = "配送费",example = "20")
    private BigDecimal freightPrice;
    @ApiModelProperty(name="coupon",value = "优惠劵减免",example = "20")
    private BigDecimal coupon;
    @ApiModelProperty(name="integral",value = "积分减免",example = "20")
    private BigDecimal integral;
    @ApiModelProperty(name="groupPon",value = "团购减免",example = "20")
    private BigDecimal groupPon;
    @ApiModelProperty(name = "totalPrice",value = "订单总价",example = "100")
    private BigDecimal totalPrice;
    @ApiModelProperty(name = "actualPrice",value = "实付金额",example = "100")
    private BigDecimal actualPrice;
    @ApiModelProperty(name = "payTime",value = "支付时间",example = "2019-01-01")
    private String payTime;
    @ApiModelProperty(name = "payId",value = "支付id",example = "xxxxxxxxxxx")
    private String payId;
    @ApiModelProperty(name = "expressId",value = "快递单号",example = "xxxxxxxxxxx")
    private String expressId;
    @ApiModelProperty(name = "expressCompany",value = "快递公司名称",example = "顺丰")
    private String expressCompany;
    @ApiModelProperty(name = "deliveryTime",value = "发货时间",example = "2019-01-01")
    private String deliveryTime;
    @ApiModelProperty(name = "confirmTime",value = "收货时间",example = "2019-01-01")
    private String confirmTime;
    @ApiModelProperty(name = "endTime",value = "订单关闭时间",example = "2019-01-01")
    private String endTime;
    @ApiModelProperty(name = "addTime",value = "添加订单时间",example = "2019-01-01")
    private String addTime;
    @ApiModelProperty(name = "updateTime",value = "修改订单时间",example = "2019-01-01")
    private String updateTime;
    @ApiModelProperty(name = "deleted",value = "逻辑删除",example = "1")
    private Integer deleted;
}
