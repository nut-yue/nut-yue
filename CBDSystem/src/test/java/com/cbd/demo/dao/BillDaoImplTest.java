package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.BillEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Date ：2019/5/31 11:30
 * @Desc ：类的介绍：个人账单
 * @author：作者：刘划轩
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class BillDaoImplTest {
    @Autowired
    private IBillDao billDao;

    @Test
    public void selectPage() throws Exception {
        Page<BillEntity> page=new Page<BillEntity>(1,5);
        QueryWrapper<BillEntity> wrapper=new QueryWrapper<BillEntity>();
        billDao.selectPage(page,null);
    }
    @Test
    public void selectList() throws Exception {
        QueryWrapper<BillEntity> wrapper=new QueryWrapper<BillEntity>();
        billDao.selectList(wrapper.eq("partlyAId",6));
    }
    @Test
    public void selectOne() throws Exception {
        QueryWrapper<BillEntity> wrapper=new QueryWrapper<BillEntity>();
        billDao.selectOne(wrapper.eq("billId",1));
    }
    @Test
    public void insert() throws Exception {
        BillEntity billEntity=new BillEntity();
        billEntity.setBillMoney(1000000);
        billDao.insert(billEntity);
    }
}
