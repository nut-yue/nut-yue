package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.CarportEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : CarportDaoImplTest
 * @Date ：2019/5/31 11:48
 * @Desc ：类的介绍：CarportDaoImpl的测试类
 * @author：作者：峗权
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarportDaoImplTest {

    @Autowired
    private ICarportDao iCarportDao;


    @Test
    public void updateByIdTest() throws Exception{
        CarportEntity carportEntity = iCarportDao.getCarport(1);
        UpdateWrapper<CarportEntity> wrapper = new UpdateWrapper<>();
        wrapper.like("carportAddress","区").set("carportStatus","已预约");
        int num = iCarportDao.updateByWrapper(null,wrapper);
        Assert.assertEquals(num,5);
    }
    @Test
    public void getCarportTest()throws Exception{
        CarportEntity carportEntity = iCarportDao.getCarport(1);
        Assert.assertNotNull(carportEntity);
    }
    @Test
    public void inserCarportTest()throws Exception{
        CarportEntity carportEntity = new CarportEntity();
        carportEntity.setCarportStatus("空闲");
        carportEntity.setCarportAddress("六环路");
        carportEntity.setCarportNumber("川A4743l");
        carportEntity.setCarportProperty("342743");
        carportEntity.setCarportPropertyDoc("011.jpg");
        carportEntity.setUserId(1);
        carportEntity.setCarportImage("11.jpg");
        int num = iCarportDao.inserCarport(carportEntity);
        Assert.assertEquals(num,1);
    }

    @Test
    public void listCarportTest()throws Exception{
        Page<CarportEntity> page = new Page<>(1,3);
        QueryWrapper<CarportEntity> queryWrapper = new QueryWrapper<>();
        String carportStatus ="空闲";
        int userId= 1;
        queryWrapper.eq("userId",userId)
                .eq(StringUtils.isNotEmpty(carportStatus),"carportStatus",carportStatus)
                .select("carportStatus","userId");
        IPage<CarportEntity> list = iCarportDao.listCarport(page,queryWrapper);

        Assert.assertNotNull(list);
    }

    @Test
    public void deleteCarportTest()throws Exception{
        int num = iCarportDao.deleteCarport(8);
        Assert.assertEquals(num,1);
    }
}
