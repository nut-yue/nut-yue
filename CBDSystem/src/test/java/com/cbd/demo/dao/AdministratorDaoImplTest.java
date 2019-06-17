package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.AdministratorEntity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : AdministratorDaoImplTest
 * @Date ：2019/5/31 14:31
 * @Desc ：类的介绍：AdministratorDaoImpl测试类
 * @author：作者：峗权
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministratorDaoImplTest {
    @Autowired
    private IAdministratorDao administratorDao;

    @Test
    public void getByAdminIdTest()throws Exception{
        AdministratorEntity administratorEntity = administratorDao.getByAdminId(1);
        Assert.assertNotNull(administratorEntity);
    }

    @Test
    public void selectOneTest()throws Exception{
        QueryWrapper<AdministratorEntity> wrapper = new QueryWrapper<>();
        int adminId = 1;
        wrapper.eq("adminId",adminId);
        AdministratorEntity administratorEntity =administratorDao.selectOne(wrapper);
        Assert.assertNotNull(administratorEntity);
    }

    @Test
    public void selectPageTest()throws Exception{
        Page<AdministratorEntity> page = new Page<>(1,3);
        IPage<AdministratorEntity> ipage = administratorDao.selectPage(page);
        Assert.assertNotNull(ipage);
    }

    @Test
    public void updateAdministratorTest()throws Exception{
        AdministratorEntity administratorEntity = administratorDao.getByAdminId(1);
        administratorEntity.setAdministratorPhone("13829430494");
        int num = administratorDao.updateAdministrator(administratorEntity);
        Assert.assertEquals(num,1);
    }

    @Test
    public void addAdministratorTest()throws Exception{
        AdministratorEntity administratorEntity = new AdministratorEntity();
        administratorEntity.setAdministratorPhone("18320495867");
        administratorEntity.setAdminId(2);
        administratorEntity.setAdministratorName("周陆程");
        int num = administratorDao.addAdministrator(administratorEntity);
        Assert.assertEquals(num,1);
    }

}
