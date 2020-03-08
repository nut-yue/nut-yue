package com.mall.clinicservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.clinicdb.entity.GoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : IGoodsService
 * @Date ：2019/12/16 19:13
 * @author：nut-yue
 */
public interface IGoodsService {

     /**
      * 获取列表数据
      * @param condition 查询条件
      * @return 查询到的列表数据
      */
     IPage<GoodsEntity> listGoods(Map<String,Object> condition);

     /**
      * 新增数据
      * @param goodsEntity 新增对象实例
      * @return 新增的条数
      */
     int insertGoods(GoodsEntity goodsEntity);

     /**
      * 根据id查询实例
      * @param goodsId 实例id
      * @return 实例对象
      */
     GoodsEntity getGoods(String goodsId);

     /**
      * 批量删除数据
      * @param goodsIds id集合
      * @return 删除成功的条数
      */
     int removeBatch(List<String> goodsIds);

     /**
      * 修改实例信息
      * @param goodsEntity 实例对象
      * @return 修改成功的条数
      */
     int updateGoods(GoodsEntity goodsEntity);


}
