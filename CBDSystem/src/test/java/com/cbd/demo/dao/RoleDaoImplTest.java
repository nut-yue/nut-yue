package com.cbd.demo.dao;

import com.cbd.demo.dao.impl.RoleDaoImpl;
import com.cbd.demo.entity.RoleEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName : IRoleDaoTest
 * @Date ：2019/5/31 15:00
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleDaoImplTest {
    @Autowired
    private IRoleDao roleDao;

    @Test
    public void addRole() {
        RoleEntity role = new RoleEntity();
        role.setRoleName("测试角色");
        int in= 0;
        try {
            in = roleDao.addRole(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(in,1);
    }
    @Test
    public void getRole() {
        RoleEntity role= null;
        try {
            role = roleDao.getRole(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(role);
    }

    @Test
    public void showAllRole() {
        try {
            Assert.assertNotNull(roleDao.showAllRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
