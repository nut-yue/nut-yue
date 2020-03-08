package com.mall.clinicservice.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.clinicdb.entity.GoodsEntity;
import com.mall.clinicdb.mapper.GoodsMapper;
import com.mall.clinicservice.service.IGoodsService;
import com.mall.clinicutil.common.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName : GoodsServiceImpl
 * @Date ：2019/12/16 19:14
 * @author：nut-yue
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private ParamUtil paramUtil;

    @Override
//    @Cacheable(value = "goods",key="#condition")
    public IPage<GoodsEntity> listGoods(Map<String,Object> condition) {
        QueryWrapper<GoodsEntity> wrapper = new QueryWrapper<GoodsEntity>();
        int currentPage = MapUtil.getInt(condition,"currentPage");
        int pageSize = MapUtil.getInt(condition,"pageSize");
        Map<String, Object> param = paramUtil.getParam(condition);
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            wrapper.like(next.getKey(), next.getValue());
        }
        Page<GoodsEntity> page = new Page<GoodsEntity>(currentPage,pageSize);
        IPage<GoodsEntity> pages = goodsMapper.selectPage(page, wrapper);
        return pages;
    }

    @Override
    public int insertGoods(GoodsEntity goodsEntity) {
        return goodsMapper.insert(goodsEntity);
    }

    @Override
    @Cacheable(value = "goods",key="#goodsId")
    public GoodsEntity getGoods(String goodsId) {
        return  goodsMapper.selectById(goodsId);
    }

    @Override
    public int removeBatch(List<String> goodsIds) {
        return goodsMapper.deleteBatchIds(goodsIds);
    }

    @Override
    @CacheEvict(value="goods",key = "#goodsEntity.getId()")
    public int updateGoods(GoodsEntity goodsEntity) {
        return goodsMapper.updateById(goodsEntity);
    }

}
