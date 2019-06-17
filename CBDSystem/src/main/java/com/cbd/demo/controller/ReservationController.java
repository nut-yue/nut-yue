package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.ReservationBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.service.IReservationService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : ReservationController
 * @Date ：2019/6/1/0001 14:48
 * @Desc ：类的介绍：预约Controller
 * @author：张皓
 */

@RestController
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    @Introduce(desc = "新增预约")
    @RequestMapping(value = "/user/addReservation",method = RequestMethod.GET)
    public ResponseData insertReservation(ReservationBean reservation,Integer userId,Integer carportId,HttpSession session){
        UserBean user = (UserBean) session.getAttribute("login");

        //车位对象
        CarportBean car = new CarportBean();
        car.setCarportId(carportId);
        //被预约者对象
        UserBean users = new UserBean();
        users.setUserId(userId);
        //将对象添加到预约对象中
        reservation.setAppointerUserBean(user);
        reservation.setAppointedUserBean(users);
        reservation.setCarportBean(car);
        ResponseData responseData = new ResponseData();
        if (reservation!=null){
            int i=reservationService.insertReservation(reservation);
            if (i>0){
                responseData.getData().put("addReservation",1);
                return responseData;
            }
        }
        return ResponseData.notFound();
    }
    @Introduce(desc = "拒绝/同意预约")
    @RequestMapping(value = "/user/updateReservation",method = RequestMethod.GET)
    public ResponseData updateReservation(HttpSession session,int userId,int carportId,ReservationBean reservation){
        //从session里取出ID
        UserBean userBean1 = (UserBean) session.getAttribute("login");

        //根据传来的reservation查错reservation对象
        ReservationBean reservationBean = reservationService.getReservation(reservation.getReservationId());
        //根据传来的userId封装预约者Id
        UserBean userBean2 = new UserBean();
        userBean2.setUserId(userId);
        //根据传来的carportId封装车位Id
        CarportBean carportBean = new CarportBean();
        carportBean.setCarportId(carportId);
        ResponseData responseData = new ResponseData();
        reservation.setReservationType(reservationBean.getReservationType());
        reservation.setAppointerUserBean(userBean2);
        reservation.setAppointedUserBean(userBean1);
        reservation.setCarportBean(carportBean);
        int i = reservationService.updateReservation(reservation);
        if (i>0){
            responseData.getData().put("updateReservation",1);
            return responseData;
        }
        return ResponseData.notFound();
    }
    @Introduce(desc = "查看我的预约")
    @RequestMapping(value = "/user/listSelfReservation",method = RequestMethod.GET)
    public ResponseData listSelfReservation(HttpSession session, String reservationContent, int currentPage, int pageSize){
        //从session里取出ID
        UserBean userBean = (UserBean) session.getAttribute("login");
        int appointerUserId=userBean.getUserId();
        ResponseData responseData = new ResponseData();
        IPage<ReservationBean> reservationBeanIPage = reservationService.listSelfReservation(appointerUserId, reservationContent, currentPage, pageSize);
        responseData.getData().put("listSelfReservation",reservationBeanIPage);
        return responseData;
    }
    @Introduce(desc = "查看待处理预约")
    @RequestMapping(value = "/user/listReservation",method = RequestMethod.GET)
    public ResponseData listReservation(HttpSession session, String reservationContent, int currentPage, int pageSize){
        //从session里取出ID
        UserBean userBean = (UserBean) session.getAttribute("login");
        int appointedUserId=userBean.getUserId();
        ResponseData responseData = new ResponseData();
        IPage<ReservationBean> reservationBeanIPage = reservationService.listReservation(appointedUserId, reservationContent, currentPage, pageSize);
        responseData.getData().put("listReservation",reservationBeanIPage);
        return responseData;
    }
    @Introduce(desc = "查看预约详情")
    @RequestMapping(value = "/user/getReservation",method = RequestMethod.GET)
    public ResponseData getReservation(int reservationId){
        ResponseData responseData = new ResponseData();
        ReservationBean reservation = reservationService.getReservation(reservationId);
        responseData.getData().put("getReservation",reservation);
        return responseData;
    }
}
