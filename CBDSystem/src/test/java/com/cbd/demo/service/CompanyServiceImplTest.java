package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.bean.CompanyBean;
import com.cbd.demo.entity.CompanyEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : CompanyServiceImplTest
 * @Date ：2019/6/2 17:12
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceImplTest {
    @Autowired
    private ICompanyService companyService;


    @Test
    public void getCompany( ) {
        CompanyBean com=companyService.getCompany(1);
        System.out.println(com);
    }


    @Test
    public void findById( ) {
        CompanyBean com= companyService.findById(1);
        System.out.println(com);
    }


    @Test
    public void removeCompany( ) {
        int in=companyService.removeCompany(12);
        System.out.println(in);
    }


    @Test
    public void updateCompany( ) {
        CompanyBean adm = new CompanyBean();
        adm.setCompanyId(10);
        adm.setCompanyName("测试");
        companyService.updateCompany(adm);
    }


    @Test
    public void saveAdministrator( ) {
        AdminBean ad = new AdminBean();
        ad.setAdminName("测试呀");
        ad.setAdminPassword("123");
        CompanyBean adm = new CompanyBean();
        adm.setCompanyName("测试");
        adm.setAdminBean(ad);
        companyService.saveAdministrator(adm);
    }

    @Test
    public void listCompany( ) {
        IPage<CompanyEntity> page=companyService.listCompany(1,10,"测试");
        System.out.println(page.getRecords().size());
    }

}
