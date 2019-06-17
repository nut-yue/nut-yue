package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.SaleBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.service.ISaleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @ClassName : ReservationServiceImplTest
 * @Date ：2019/06/03 14:14
 * @Desc ：类的介绍：SaleService测试
 * @author：陈云强
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SaleServiceImplTest {
    @Autowired
    private ISaleService saleService;

    @Test
    public void findAllSaleInfo() {
        IPage<SaleBean> allSaleInfo = saleService.findAllSaleInfo(null, 1, 20);
//        System.out.println(allSaleInfo.getRecords());
        for (SaleBean record : allSaleInfo.getRecords()) {
            System.out.println(record);
        }
//        Assert.assertNotNull(allSaleInfo);
    }

    @Test
    public void getSale() {
        SaleBean sale = saleService.getSale(1);
        Assert.assertNotNull(sale);
    }

    @Test
    public void addSale() {
        SaleBean saleBean = new SaleBean();
        saleBean.setSalePrice(100);
        CarportBean carportBean = new CarportBean();
        carportBean.setCarportId(1);
        saleBean.setCarportBean(carportBean);
        UserBean userBean = new UserBean();
        userBean.setUserId(1);
        saleBean.setUserBean(userBean);
        int i = saleService.addSale(saleBean);
        Assert.assertNotEquals(0,i);
    }
}