package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.bean.AdministratorBean;
import com.cbd.demo.entity.AdministratorEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : AdministratorServiceImplTest
 * @Date ：2019/6/2 17:10
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministratorServiceImplTest {
    @Autowired
    private IAdministratorService administratorService;

    @Test
    public void getAdministrator(){
        AdministratorBean adm=administratorService.getAdministrator(1);
        System.out.println(adm);
    }

    @Test
    public void findById( ){
        AdministratorBean adm=administratorService.findById(1);
        System.out.println(adm);
    }

    @Test
    public void  listAdministrator(){
        IPage<AdministratorEntity> page=administratorService.listAdministrator(1,10);
        System.out.println(page.getRecords().size());
    }
    @Test
    public void  updateAdministrator( ){
        AdministratorBean administratorBean = new AdministratorBean();
        administratorBean.setAdministratorId(5);
        administratorBean.setAdministratorName("张三");
        int in=administratorService.updateAdministrator(administratorBean);
        System.out.println(in);
    }
    @Test
    public void  insertAdministrator( ){
        AdminBean adminBean = new AdminBean();
        adminBean.setAdminPassword("1231");
        adminBean.setAdminName("测试");

        AdministratorBean adm = new AdministratorBean();
        adm.setAdministratorName("测试");
        adm.setAdministratorPhone("123131321");
        adm.setAdminBean(adminBean);
        administratorService.insertAdministrator(adm);
    }

    @Test
    public void del(){
        int in = administratorService.delAdministrator(1);
        System.out.println(in);
    }
    @Test
    public void updateRole(){
        administratorService.updateRole(1,2);
    }
}
