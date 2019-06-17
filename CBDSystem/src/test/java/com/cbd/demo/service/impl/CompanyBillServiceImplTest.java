package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.DealCountBean;
import com.cbd.demo.entity.CompanyBillEntity;
import com.cbd.demo.service.ICompanyBillService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
/**
 * @ClassName : PerformanceServiceImplTest
 * @Date ：2019/6/03 12:40
 * @Desc ：类的介绍：企业账单实现类
 * @author：刘划轩
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyBillServiceImplTest {
    @Autowired
    private ICompanyBillService companyBillService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listCompanyBill() {
        IPage<CompanyBillEntity> page=companyBillService.listCompanyBill(1,2,null,"2018-12-01","2019-04-01");
        Assert.assertNotNull(page);
    }

    @Test
    public void listCompanyBill1() {
        IPage<CompanyBillEntity> page=companyBillService.listCompanyBill(1,1,2,null,"2018-12-01","2019-04-01");
        Assert.assertNotNull(page);
    }

    @Test
    public void getCompanyBill() {
        Assert.assertNotNull(companyBillService.getCompanyBill(11));
    }

    @Test
    public void getMoney() {
        DealCountBean dealCountBean=companyBillService.getMoney(1);
        Assert.assertNotNull(dealCountBean);
    }
}