package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.CompanyEntity;
import com.cbd.demo.mapper.CompanyMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : CompanyDaoImplTest
 * @Date ：2019/5/31/0031 17:09
 * @Desc ：类的介绍：
 * @author：张皓
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyDaoImplTest {
    @Autowired
    private CompanyMapper companyMapper;

    @Test
    public void selectByAdminIdTest(){
        QueryWrapper<CompanyEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("companyId",1);
        CompanyEntity companyEntity = companyMapper.selectOne(queryWrapper);
        Assert.assertNotNull(companyEntity);
    }
    @Test
    public void insertCompanyTest(){
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName("hhh");
        int insert = companyMapper.insert(companyEntity);
        Assert.assertNotEquals(0,insert);
    }
    @Test
    public void deleteByIdTest(){
        int i = companyMapper.deleteById(1);
        Assert.assertNotEquals(0,i);

    }
    @Test
    public void updateByIdTest(){
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName("hhh");
        companyEntity.setCompanyId(2);
        QueryWrapper<CompanyEntity> queryWrapper = new QueryWrapper<>();
        int update = companyMapper.update(companyEntity, queryWrapper);
        Assert.assertNotEquals(0,update);
    }

    @Test
    public void selectPageTest(){
        IPage<CompanyEntity> page = new Page<>(1,2);
        QueryWrapper<CompanyEntity> queryWrapper = new QueryWrapper<>();

        IPage<CompanyEntity> page1 = companyMapper.selectPage(page, queryWrapper);
        Assert.assertNotNull(page1);
    }
}
