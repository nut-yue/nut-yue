package com.cbd.demo.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.CompanyBillEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName : CompanyBillDaoImplTest
 * @Date ：2019/5/31 11:08
 * @Desc ：类的介绍：
 * @author：岳超
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyBillDaoImplTest {

    @Autowired
    private ICompanyBillDao CompanyBillDao;

    @Test
    public void listCompanyBillTest() {
        Page<CompanyBillEntity> page = new Page<>(1,2);
        QueryWrapper <CompanyBillEntity> wrapper = new QueryWrapper<CompanyBillEntity>();
        wrapper.like("companyBillPactType","外部");
        IPage<CompanyBillEntity> companyBillEntityIPage = null;
        try {
            companyBillEntityIPage = CompanyBillDao.listCompanyBill(page,wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(companyBillEntityIPage);
    }
    @Test
    public void getCompanyBillTest() {
        CompanyBillEntity companyBill = null;
        try {
            companyBill = CompanyBillDao.getCompanyBill(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(companyBill);
    }
    @Test
    public void getCompanyBill() {
        QueryWrapper <CompanyBillEntity> wrapper = new QueryWrapper<CompanyBillEntity>();
        wrapper.like("companyBillPactType","外部");
        List<CompanyBillEntity> companyBill = null;
        try {
            companyBill = CompanyBillDao.getCompanyBill(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(companyBill);
    }

}
