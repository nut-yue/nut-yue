package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.PersonalEntity;
import com.cbd.demo.mapper.PersonalMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : PersonalDaoImplTest
 * @Date ：2019/5/31/0031 15:27
 * @Desc ：类的介绍：个人买卖车合同持久层实现测试类
 * @author：张皓
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalDaoImplTest {
    @Autowired
    private PersonalMapper personalMapper;
    @Test
    public void insertTest(){
        PersonalEntity personalEntity = new PersonalEntity();
        personalEntity.setPersonalPrice(100);
        int insert = personalMapper.insert(personalEntity);
        Assert.assertNotEquals(0,insert);
    }
    @Test
    public void updateTest(){
        PersonalEntity personalEntity = new PersonalEntity();
        personalEntity.setPersonalPrice(99);
        QueryWrapper<PersonalEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("personalId",10);
        int update = personalMapper.update(personalEntity, queryWrapper);
        Assert.assertNotEquals(0,update);
    }
    @Test
    public void selectByIdTest(){
        PersonalEntity personalEntity = personalMapper.selectById(1);
        Assert.assertNotNull(personalEntity);
    }
    @Test
    public void selectPagePage(){
        IPage<PersonalEntity> iPage = new Page<>(1,2);
        QueryWrapper<PersonalEntity> queryWrapper = new QueryWrapper<>();
        IPage<PersonalEntity> iPage1 = personalMapper.selectPage(iPage, queryWrapper);
        Assert.assertNotNull(iPage1);
    }
}
