package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.ReservationBean;

/**
 * @ClassName : IReservation
 * @Date ：2019/5/28 15:35
 * @Desc ：类的介绍：预约业务接口
 * @author：作者：岳超
 */
public interface IReservationService {

    /**
     * 1.买方（租方）点击预约,向预约表中添加一条预约记录，预约类型为买（租），是否处理为未处理，
     * 处理内容为“留言内容”，预约者id为发起预约者id,被预约者id为卖方（出租方）id；
     * 2.向被预约者发送一条消息
     * @param reservation 预约记录
     * @return 生成预约记录条数，返回 1：添加成功 0：添加失败
     */
    int insertReservation(ReservationBean reservation);

    /**
     *  1.卖方（出租方）处理预约，修改该预约记录状态为已处理，是否处理为同意预约（拒绝预约）,同时将
     *  预约该车位的其他预约消息改为已处理,车位状态改为已预约,买卖的话同时生成合同，
     *  2.向预约者发送一条消息,提示对方已回复预约,请查收
     * @param reservation 预约记录
     * @return 生成修改预约记录条数，返回 1：修改成功 0：修改失败
     */
    int  updateReservation(ReservationBean reservation);


     /**
     * 动态分页查看自己预约记录，展示的时候需要将每一条预约车位的车位信息一起封装到分页对象中
     * 需要注意，reservationDeal有三种值：已拒绝，已同意，和待处理，对应的是数据库中的
     * reservationDeal字段
     * @param appointerUserId 预约者的id,即当前操作者id
     * @param reservationContent 处理内容
     * @param currentPage 当前页数
     * @param pageSize 每页显示条数
     * @return 当前用户的所有车辆预约信息分页对象
     */
    IPage<ReservationBean> listSelfReservation(int appointerUserId, String reservationContent, int currentPage, int pageSize);

    /**
     * 动态分页查看自己被预约记录，即自己是车位拥有者，展示的时候需要将每一个预约车位的车位信息一起
     * 封装到分页对象中reservationDeal有三种值：已拒绝，已同意，和待处理，对应的是数据库中的
     * reservationDeal字段
     * @param appointedUserId 被预约者id,即当前操作者id
     * @param reservationContent 处理内容
     * @param currentPage 当前页数
     * @param pageSize 每页显示条数
     * @return 当前用户被预约记录分页对象
     */
    IPage<ReservationBean> listReservation(int appointedUserId, String reservationContent, int currentPage, int pageSize);

    /**
     * 根据预约信息id查看预约详情，同时封装车辆详细信息及预约者的个人信息
     * @param reservationId 预约信息id
     * @return 预约对象
     */
    ReservationBean getReservation(int reservationId);
}
