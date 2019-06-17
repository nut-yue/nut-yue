package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.RentEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName : RentDaoImplTest
 * @Date ：2019/5/31 14:35
 * @Desc ：类的介绍：租借信息持久层接口测试
 * @author：作者：周陆成
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RentDaoImplTest {
    @Autowired
    private IRentDao rentDao;

    /**
     * 添加租借信息
     * @throws Exception
     */
    @Test
   public void addRent()throws Exception{
       RentEntity rentEntity=new RentEntity();
       rentEntity.setRentEndDate("2019-05-31");
       rentEntity.setRentPrice(456);
       rentEntity.setRentStartDate("2019-05-31");
       Assert.assertNotNull(rentDao.insert(rentEntity));
   }

    /**
     * 根据租借信息id查看租借实体信息
     * @throws Exception
     */
   @Test
   public void findById()throws Exception{
        RentEntity rentEntity=rentDao.selectById(11);
        Assert.assertNotNull(rentEntity);
   }

    /**
     * 根据租借信息动态查询 并分页
     * @throws Exception
     */
   @Test
   public void findRentByPage()throws Exception{
       QueryWrapper<RentEntity>wrapper=new QueryWrapper<RentEntity>();
       wrapper.eq("rentPrice","400");
       Page<RentEntity>page=new Page<RentEntity>();
        page.setSize(1);
        page.setCurrent(1);
       IPage<RentEntity> rentEntityPage= rentDao.selectPage(page,wrapper);
       Assert.assertNotNull(rentEntityPage);
   }

    /**
     * 根据租借id删除租借信息
     * @throws Exception
     */
   @Test
   public void delById()throws Exception{
       UpdateWrapper<RentEntity> wrapper = new UpdateWrapper<>();
       wrapper.eq("rentId",12);
        Assert.assertNotNull(rentDao.deleteByCondition(wrapper));
   }
}
