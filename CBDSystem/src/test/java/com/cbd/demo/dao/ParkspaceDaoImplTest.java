package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.ParkspaceEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : ParkspaceDaoTest
 * @Date ：2019/5/31 13:20
 * @Desc ：类的介绍：cdb车位dao测试类
 * @author：作者：胡平
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkspaceDaoImplTest {

    @Autowired
    private IParkspaceDao parkspaceDao;


    @Test
    public void getById() throws Exception {
        ParkspaceEntity parkspaceEntity = parkspaceDao.selectById(1);
//        System.out.println(parkspaceEntity);
        Assert.notNull(null, parkspaceEntity);
    }

    @Test
    public void insert() throws Exception {
        ParkspaceEntity parkspaceEntity = new ParkspaceEntity();
        parkspaceEntity.setParkspaceAddress("21312");
        parkspaceEntity.setParkspaceNumber("123");
        int insert = parkspaceDao.insert(parkspaceEntity);
        Assert.eq(insert, 1, null);
    }

    @Test
    public void update() throws Exception {
        ParkspaceEntity parkspaceEntity = new ParkspaceEntity();
        parkspaceEntity.setParkspaceId(11);
        parkspaceEntity.setParkspaceAddress("大家好2");
        parkspaceEntity.setParkspaceNumber("123");
        UpdateWrapper<ParkspaceEntity> wrapper = new UpdateWrapper<>();
        wrapper.like(true, "parkspaceId", 11);
        int insert = parkspaceDao.updateById(parkspaceEntity);
        System.out.println(insert);
        Assert.eq(insert, 1, null);
    }


    @Test
    public void listPage() throws Exception {
        IPage<ParkspaceEntity> page = new Page<>();
        page.setSize(10);
        page.setPages(0);
        IPage<ParkspaceEntity> iPage = parkspaceDao.selectPage(page, null);
        Assert.notNull(null, iPage);

    }

}
