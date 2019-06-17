package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.CompanyBean;
import com.cbd.demo.bean.TenantryBean;
import com.cbd.demo.entity.TenantryEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName : TenantryServiceImplTest
 * @Date ：2019/6/2 11:38
 * @Desc ：类的介绍：
 * @author：岳超
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TenantryServiceImplTest  {

    @Autowired
    private ITenantryService tenantryService;

    @Test
    public void addNewTenantryTest(){
        TenantryBean tenantryBean = new TenantryBean();
        tenantryBean.setTenantryContractNum("测试");
        tenantryBean.setTenantryDealPrice(30000);
        tenantryBean.setTenantryUserName("测试");
        tenantryBean.setTenantryStartTime("2015-12-17");
        tenantryBean.setTenantryEndTime("2016-06-30");
        tenantryBean.setTenantryLinkman("测试");
        tenantryBean.setTenantryLinkmanTel("123546");
        tenantryBean.setTenantryProfession("2.jpg");
        tenantryBean.setTenantryOriginalId(0);
        tenantryBean.setTenantryContractStatus("已生效");
        tenantryBean.setTenantryContact("公司负责人");
        tenantryBean.setTenantryPhone("1111111");
        CompanyBean companyBean = new CompanyBean();
        companyBean.setCompanyId(10);
        List <Integer> ids = Arrays.asList(1,2,3);
        tenantryBean.setCompanyBean(companyBean);
        int result = tenantryService.addNewTenantry(tenantryBean,ids);
        Assert.assertEquals(result,1);
    }

    @Test
    public void contractTenantry(){
        TenantryBean tenantryBean = new TenantryBean();
        tenantryBean.setTenantryContractNum("测试");
        tenantryBean.setTenantryDealPrice(30000);
        tenantryBean.setTenantryUserName("测试");
        tenantryBean.setTenantryStartTime("2015-12-17");
        tenantryBean.setTenantryEndTime("2020-06-30");
        tenantryBean.setTenantryLinkman("测试");
        tenantryBean.setTenantryLinkmanTel("123546");
        tenantryBean.setTenantryProfession("2.jpg");
        tenantryBean.setTenantryOriginalId(17);
        tenantryBean.setTenantryContractStatus("已生效");
        tenantryBean.setTenantryContact("公司负责人");
        tenantryBean.setTenantryPhone("1111111");
        CompanyBean companyBean = new CompanyBean();
        companyBean.setCompanyId(10);
        tenantryBean.setCompanyBean(companyBean);
        List <Integer> ids = Arrays.asList(1,2,3);
        int result = tenantryService.contractTenantry(tenantryBean,ids);
        Assert.assertEquals(result,1);
    }

    @Test
    public void updateTenantryTest(){
        int result = tenantryService.updateTenantry(10);
        Assert.assertEquals(result,1);
    }

    @Test
    public void showAllTenantryTest(){
        IPage<TenantryEntity> page = tenantryService.showAllTenantry(1, 5, null, "2015-10-15", "测试");
        page.getRecords().forEach(System.out::println);
        Assert.assertNotNull(page);
    }

    @Test
    public void findById(){
        TenantryBean tenantryBean = tenantryService.findById(10);
        System.out.println(tenantryBean);
        Assert.assertNotNull(tenantryBean);
    }
}
