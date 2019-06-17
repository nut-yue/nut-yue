package com.cbd.demo.dao;

import com.cbd.demo.entity.UserEntity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : UserDaoImplTest
 * @Date ：2019/5/31 10:58
 * @Desc ：类的介绍：个人用户持久层测试
 * @author：作者：陈玉婷
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoImplTest {

    @Autowired
    private IUserDao userDao;
    @Test
    public void selectById() {
        UserEntity user = null;
        try {
            user = userDao.getById(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(user);
    }
    @Test
    public void updateById(){

        UserEntity user1 = new UserEntity();
        user1.setUserId(1);
        user1.setUserRealName("测试");
        user1.setUserAddress("金牛区");
        user1.setUserComplaint(123);
        user1.setUserDeal(123456789);
        user1.setUserPhone("08251231");
        user1.setUserEmailAddress("qq113837");
        user1.setUserProfession("测试测试");
        user1.setUserIdCard("12334599558885588");
        user1.setAdminId(2);
        int result = 0;
        try {
            result = userDao.updateById(user1);
            Assert.assertEquals(result,1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void insert(){
        UserEntity user = new UserEntity();
       user.setUserRealName("添加");
       user.setUserAddress("青羊区");
       user.setUserComplaint(123);
       user.setUserDeal(12355);
       user.setUserPhone("02484");
       user.setUserEmailAddress("红瓦是");
       user.setUserProfession("测试2");
       user.setUserIdCard("1111");
       user.setAdminId(4);
        int value = 0;
        try {
            value = userDao.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(value,1);
    }
}
