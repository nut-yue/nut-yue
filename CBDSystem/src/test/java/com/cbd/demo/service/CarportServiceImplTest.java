package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.entity.CarportEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : CarportServiceImplTest
 * @Date ：2019/5/31 15:14
 * @Desc ：类的介绍：
 * @author：岳超
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarportServiceImplTest {

    @Autowired
    private ICarportService iCarportService;

    @Test
    public void saveCarportTest(){
        CarportBean carportBean = new CarportBean();
        carportBean.setCarportAddress("龙泉驿区");
        carportBean.setCarportNumber("123456");
        carportBean.setCarportProperty("1515");
        carportBean.setCarportPropertyDoc("01.jpg");
        carportBean.setCarportImage("1.jpg");
        UserBean userBean = new UserBean();
        userBean.setUserId(1);
        carportBean.setUserBean(userBean);
        int result = iCarportService.saveCarport(carportBean);
        Assert.assertEquals(result,1);
    }

    @Test
    public void listCarportTest(){
        IPage<CarportEntity> pages = iCarportService.listCarport(1, 1, 3, "空闲");
        pages.getRecords().forEach(System.out::println);
        Assert.assertNotNull(pages);
    }

    @Test
    public void getCarportTest(){
        CarportBean carport = iCarportService.getCarport(1);
        System.out.println(carport);
        Assert.assertNotNull(carport);
    }

    @Test
    public void updateCarportStatusTest(){
        CarportBean carportBean = new CarportBean();
        carportBean.setCarportId(8);
        Assert.assertNotNull(iCarportService.updateCarportStatus(carportBean));
    }

    @Test
    public void updateStatusTest(){
        CarportBean carportBean = new CarportBean();
        carportBean.setCarportId(8);
        Assert.assertNotNull(iCarportService.updateStatus(carportBean));
    }

    @Test
    public void deleteCarportTest(){
        int result =iCarportService.deleteCarport(13);
        Assert.assertEquals(result,1);
    }
}
