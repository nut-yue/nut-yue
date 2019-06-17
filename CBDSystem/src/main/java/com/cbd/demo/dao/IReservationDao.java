package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.ReservationEntity;

import java.io.Serializable;

/**
 * @ClassName : IReservation
 * @Date ：2019/5/31 10:30
 * @Desc ：类的介绍：预约持久化接口
 * @author：作者：岳超
 */
public interface IReservationDao {

    /**
     * 添加预约记录
     * @param reservationEntity 预约记录对象
     * @return 操作记录条数
     */
    int insert(ReservationEntity reservationEntity) throws Exception;

    /**
     * 修改预约对象状态
     * @param reservationEntity 预约对象状态
     * @param updateWrapper 条件构造器
     * @return
     */
    int update(ReservationEntity reservationEntity, Wrapper<ReservationEntity> updateWrapper) throws Exception;

    /**
     * 根据预约记录id查看预约信息详情
     * @param reservationId 预约记录id
     * @return 预约对象
     */
    ReservationEntity selectById(Serializable reservationId) throws Exception;

    /**
     * 按条件分页查询预约记录
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return 预约记录分页对象
     */
    IPage<ReservationEntity> selectPage(IPage<ReservationEntity> page, Wrapper<ReservationEntity> queryWrapper) throws Exception;

}
