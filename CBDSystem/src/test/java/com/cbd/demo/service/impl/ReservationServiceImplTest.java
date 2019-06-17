package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.ReservationBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.dao.IReservationDao;
import com.cbd.demo.entity.ReservationEntity;
import com.cbd.demo.service.IReservationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
/**
 * @ClassName : ReservationServiceImplTest
 * @Date ：2019/06/02 15:14
 * @Desc ：类的介绍：
 * @author：张皓
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceImplTest {
@Autowired
private IReservationService reservationService;
    @Test
    public void insertReservation() {
        ReservationBean reservationBean = new ReservationBean();
        reservationBean.setReservationDeal("否");
        reservationBean.setReservationType("买");
        reservationBean.setReservationContent("100卖不卖");
        UserBean userBean = new UserBean();
        userBean.setUserId(5);
        UserBean userBean1 = new UserBean();
        userBean1.setUserId(6);
        CarportBean carportBean = new CarportBean();
        carportBean.setCarportId(3);
        reservationBean.setCarportBean(carportBean);
        reservationBean.setAppointedUserBean(userBean);
        reservationBean.setAppointerUserBean(userBean1);
        int i = reservationService.insertReservation(reservationBean);
        Assert.assertNotEquals(0,i);
    }

    @Test
    public void updateReservation() {
        ReservationBean reservationBean = new ReservationBean();
        reservationBean.setReservationId(4);
        reservationBean.setReservationDeal("同意预约");
        reservationBean.setReservationType("买");
        reservationBean.setReservationContent("100卖不卖");
        UserBean userBean = new UserBean();
        userBean.setUserId(5);
        UserBean userBean1 = new UserBean();
        userBean1.setUserId(6);
        CarportBean carportBean = new CarportBean();
        carportBean.setCarportId(3);
        reservationBean.setCarportBean(carportBean);
        reservationBean.setAppointedUserBean(userBean);
        reservationBean.setAppointerUserBean(userBean1);
        int i = reservationService.updateReservation(reservationBean);
        Assert.assertNotEquals(0,i);
    }

    @Test
    public void listSelfReservation() {
        IPage<ReservationBean> reservationEntityIPage = reservationService
                .listSelfReservation(6,null,1, 2);
        Assert.assertNotNull(reservationEntityIPage);
    }

    @Test
    public void listReservation() {
        IPage<ReservationBean> reservationEntityIPage = reservationService
                .listReservation(6,null,1, 2);
        Assert.assertNotNull(reservationEntityIPage);
    }

    @Test
    public void getReservation() {
        ReservationBean reservation = reservationService.getReservation(1);
        Assert.assertNotNull(reservation);
    }
}