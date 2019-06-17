package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.RentBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : RentServiceImplTest
 * @Date ：2019/6/1 18:15
 * @Desc ：类的介绍：
 * @author：岳超
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RentServiceImplTest {

    @Autowired
    private IRentService rentService;

    @Test
    public void insertRentTest(){
        RentBean rentBean = new RentBean();
        rentBean.setRentStartDate("2015-11-11");
        rentBean.setRentEndDate("2016-11-11");
        rentBean.setRentPrice(3000);
        CarportBean carportBean = new CarportBean();
        carportBean.setCarportId(12);
        rentBean.setCarportBean(carportBean);
        rentService.insertRent(rentBean);
    }

    @Test
    public void getRentTest(){
        RentBean rent = rentService.getRent(10);
        System.out.println(rent);
        Assert.assertNotNull(rent);
    }

    @Test
    public void listRentTest(){
        IPage<RentBean> page = rentService.listRent("路", 1, 4);
        page.getRecords().forEach(System.out::println);
        Assert.assertNotNull(page);
    }
}
