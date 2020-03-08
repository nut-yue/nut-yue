package com.mall.clinicservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.clinicdb.entity.CartEntity;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : ICartService
 * @Date ：2020/1/20 23:08
 * @author：nut-yue
 */
public interface ICartService  {

    /**
     * 获取列表数据
     * @param condition 查询条件
     * @return 查询到的列表数据
     */
    IPage<CartEntity> listCart(Map<String,Object> condition);

    /**
     * 新增购物车数据
     * @param cartEntity 新增对象实例
     * @return 新增的条数
     */
    int insertGoods(CartEntity cartEntity);

    /**
     * 修改购物车商品信息（商品数量）
     * @param cartEntity 购物车对象
     * @return 新增的条数
     */
    int updateCart(CartEntity cartEntity);

    /**
     * 删除购物车
     * @param cartIds 购物车id
     * @return 新增的条数
     */
    int deleteCart(List <Integer> cartIds);
}