package com.cbd.demo.service;

import com.cbd.demo.bean.AdminBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : AdminServiceImplTest
 * @Date ：2019/6/2 17:08
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceImplTest {
    @Autowired
    private IAdminService adminService;

    @Test
    public void getAdmin() {
        AdminBean adminBean=adminService.getAdmin("wq1");
        System.out.println(adminBean);
    }


    @Test
    public void getAdmins() {
        AdminBean adminBean= adminService.getAdmin("wq1","123456");
        System.out.println(adminBean);
    }


    @Test
    public void updateAdmin() {
        int in =adminService.updateAdmin(2,"10");
        System.out.println(in);
    }
}
