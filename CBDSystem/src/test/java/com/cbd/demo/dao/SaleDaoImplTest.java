package com.cbd.demo.dao;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.dao.impl.SaleDaoImpl;
import com.cbd.demo.entity.SaleEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterRestTemplateCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaleDaoImplTest {
    @Autowired
    ISaleDao iSaleDao=new SaleDaoImpl();

    @Test
    public void findAllSaleInfoTest()throws Exception{
        IPage<SaleEntity> page = new Page<>(1,2);
        QueryWrapper<SaleEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("carportId","1");
        IPage<SaleEntity> allSaleInfo = iSaleDao.findAllSaleInfo(page, queryWrapper);

        Assert.assertNotNull(allSaleInfo);
    }
    @Test
    public void findBySaleIdTest()throws Exception{
        SaleEntity Sale = iSaleDao.findBySaleId(1);

        Assert.assertNotNull(Sale);
    }

    @Test
    public void addSaleTest()throws Exception{
        SaleEntity saleEntity=new SaleEntity();
        saleEntity.setCarportId(1);
        saleEntity.setSalePrice(100);
        int i = iSaleDao.addSale(saleEntity);

        Assert.assertNotEquals(0,i);
    }

    @Test
    public void delSaleTest()throws Exception{
        int i = iSaleDao.delSale(11);

        Assert.assertNotEquals(0,i);
    }
}
