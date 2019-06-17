package com.cbd.demo.service;

import com.cbd.demo.bean.UserBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : UserServiceImplTest
 * @Date ：2019/6/1 21:14
 * @Desc ：类的介绍：
 * @author：作者：周陆成
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private IUserService userService;

    /**
     * 根据用户id获取用户信誉度
     */
    @Test
    public void findXy(){
        UserBean userBean=userService.getUser(3);
        System.out.println(userBean);
//        Assert.assertNotNull(userBean);
    }

    /**
     * 修改用户信息
     */
    @Test
    public void upUser(){
        UserBean userBean1=userService.getUser(11);
        userBean1.setUserComplaint(1);
        userBean1.setUserDeal(5);
        int i=userService.updateUser(userBean1);
        System.out.println(i);
        Assert.assertNotNull(i);
    }
}
