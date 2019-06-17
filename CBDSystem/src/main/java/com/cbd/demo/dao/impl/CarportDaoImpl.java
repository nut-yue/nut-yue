package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.ICarportDao;
import com.cbd.demo.entity.CarportEntity;
import com.cbd.demo.mapper.CarportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @ClassName : CarportDaoImpl
 * @Date ：2019/5/31 11:20
 * @Desc ：类的介绍：ICarportDao的实现类
 * @author：作者：峗权
 */
@Repository
public class CarportDaoImpl implements ICarportDao {
    @Autowired
    private CarportMapper carportMapper;

    @Override
    public int updateByWrapper(CarportEntity carport,Wrapper<CarportEntity> updateWrapper) throws Exception{
        return carportMapper.update(carport,updateWrapper);
    }

    @Override
    public CarportEntity getCarport(Serializable id) throws Exception{
        return carportMapper.selectById(id);
    }

    @Override
    public int inserCarport(CarportEntity carport)throws Exception {
        return carportMapper.insert(carport);
    }

    @Override
    public IPage<CarportEntity> listCarport(IPage<CarportEntity> page, Wrapper<CarportEntity> wrapper) throws Exception {
        return carportMapper.selectPage(page,wrapper);
    }

    @Override
    public int deleteCarport(Serializable carportId) throws Exception {
        return carportMapper.deleteById(carportId);
    }
}
