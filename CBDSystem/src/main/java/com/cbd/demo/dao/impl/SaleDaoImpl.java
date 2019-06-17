package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.ISaleDao;
import com.cbd.demo.entity.RentEntity;
import com.cbd.demo.entity.SaleEntity;
import com.cbd.demo.mapper.SaleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository
public class SaleDaoImpl implements ISaleDao {
    @Autowired
    private SaleMapper saleMapper;
    @Override
    public IPage<SaleEntity> findAllSaleInfo(IPage<SaleEntity> page, Wrapper<SaleEntity> queryWrapper)throws Exception {
        return saleMapper.selectPage(page,queryWrapper);
    }

    @Override
    public SaleEntity selectSale(Wrapper<SaleEntity> queryWrapper) {
        return saleMapper.selectOne(queryWrapper);
    }

    @Override
    public SaleEntity findBySaleId(Serializable saleId)throws Exception {
        return saleMapper.selectById(saleId);
    }

    @Override
    public int addSale(SaleEntity saleEntity)throws Exception {
        return saleMapper.insert(saleEntity);
    }

    @Override
    public int delSale(Serializable saleId)throws Exception {
        return saleMapper.deleteById(saleId);
    }

    @Override
    public int delByCondition(Wrapper<SaleEntity> wrapper) throws Exception {
        return saleMapper.delete(wrapper);
    }

}
