package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.cbd.demo.entity.PowerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName : PowerDaoImplTest
 * @Date ：2019/5/31 17:05
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerDaoImplTest {
    @Autowired
    private IPowerDao powerDao;
   @Test
    public void listAllPower() {
       try {
           List list = powerDao.listAllPower(1);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

}
