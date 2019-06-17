package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.IReservationDao;
import com.cbd.demo.entity.ReservationEntity;
import com.cbd.demo.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author ：杨强
 * @Desc ：类的介绍：预约持久化接口实现类
 * @date ：Created in 2019/5/31 0031 14:15
 */
@Repository
public class ReservationDaoImpl implements IReservationDao {
    @Autowired
    private ReservationMapper reservationMapper;
    @Override
    public int insert(ReservationEntity reservationEntity) throws Exception {
        return reservationMapper.insert(reservationEntity);
    }

    @Override
    public int update(ReservationEntity reservationEntity, Wrapper<ReservationEntity> updateWrapper) throws Exception {
        return reservationMapper.update(reservationEntity,updateWrapper);
    }

    @Override
    public ReservationEntity selectById(Serializable reservationId) throws Exception {
        return reservationMapper.selectById(reservationId);
    }

    @Override
    public IPage<ReservationEntity> selectPage(IPage<ReservationEntity> page, Wrapper<ReservationEntity> queryWrapper) throws Exception {
        return reservationMapper.selectPage(page,queryWrapper);
    }
}
