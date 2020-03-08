package com.mall.clinicservice.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.clinicdb.entity.CartEntity;
import com.mall.clinicdb.mapper.CartMapper;
import com.mall.clinicservice.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : CartServiceImpl
 * @Date ：2020/1/20 23:14
 * @author：nut-yue
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public IPage<CartEntity> listCart(Map<String, Object> condition) {
        int currentPage = MapUtil.getInt(condition,"currentPage");
        int pageSize = MapUtil.getInt(condition,"pageSize");
        QueryWrapper<CartEntity> wrapper = new QueryWrapper<>();
        wrapper.allEq(condition);
        Page<CartEntity> page = new Page<>(currentPage,pageSize);
        return cartMapper.selectPage(page,wrapper);
    }

    @Override
    public int insertGoods(CartEntity cartEntity) {
        return cartMapper.insert(cartEntity);
    }

    @Override
    public int updateCart(CartEntity cartEntity) {
        return cartMapper.update(cartEntity,null);
    }

    @Override
    public int deleteCart(List<Integer> cartIds) {
        return cartMapper.deleteBatchIds(cartIds);
    }
}
