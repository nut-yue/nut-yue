package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cbd.demo.entity.AdminEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : AdminDaoIpmlTest
 * @Date ：2019/5/31 17:28
 * @Desc ：用户Dao测试
 * @author：作者：陈云强
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminDaoIpmlTest {
    @Autowired
    private IAdminDao iAdminDao;
    @Test
    public void selectOne(){
        QueryWrapper<AdminEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("adminId","10").select("adminId","adminName");
        try {
            AdminEntity adminEntity = iAdminDao.selectOne(wrapper);
            Assert.assertNotNull(adminEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateById(){
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAdminName("皮皮虾");
        UpdateWrapper<AdminEntity> update = new UpdateWrapper<>();
        update.eq("adminId",10);
        try {
            int result= iAdminDao.updateById(adminEntity,update);
            Assert.assertEquals(1,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert(){
        AdminEntity admin=new AdminEntity();
        admin.setAdminName("测试");
        admin.setAdminPassword("123");
        admin.setAdminRoleType(1);
        try {
            int insert = iAdminDao.insert(admin);
            Assert.assertNotEquals(0,insert);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
