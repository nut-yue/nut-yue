package com.cbd.demo.service;


import com.cbd.demo.entity.PowerEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName : PowerServiceTest
 * @Date ：2019/6/3 11:24
 * @Desc ：权限业务层测试类
 * @author：作者：lvyong
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerServiceTest {
    @Autowired
    private IPowerService iPowerService;
     @Test
    public void look() throws Exception {
        List<PowerEntity>list=iPowerService.listPower(1);
        System.out.println(list);
        Assert.assertNotNull(list);
    }

}
