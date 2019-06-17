package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.IRentDao;
import com.cbd.demo.entity.RentEntity;
import com.cbd.demo.mapper.RentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
/**
 * @ClassName : RentDaoImpl
 * @Date ：2019/5/31 14:22
 * @Desc ：类的介绍：租借信息持久层接口
 * @author：作者：周陆成
 */
@Repository
public class RentDaoImpl implements IRentDao {
    @Autowired
    private RentMapper rentMapper;

    /**
     * 添加招租信息
     * @param rentEntity 租借展示信息对象
     * @return 影响行数 大于0为成功
     * @throws Exception
     */
    @Override
    public int insert(RentEntity rentEntity) throws Exception {
        return rentMapper.insert(rentEntity);
    }

    /**
     * 按租借id查看招租对象信息
     * @param rentId 主键id
     * @return 租借展示信息对象
     * @throws Exception
     */
    @Override
    public RentEntity selectById(Serializable rentId) throws Exception {
        RentEntity rentEntity=rentMapper.selectById(rentId);
        return rentEntity;
    }

    /**
     * 招租信息
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return 租借展示信息分页对象集合
     * @throws Exception
     */
    @Override
    public IPage<RentEntity> selectPage(IPage<RentEntity> page, Wrapper<RentEntity> queryWrapper) throws Exception {
        return rentMapper.selectPage(page,queryWrapper);
    }

    /**
     * 按车位id删除租借信息
     * @param wrapper  条件构造器
     * @return 影响行数 1未真
     * @throws Exception
     */
    @Override
    public int deleteByCondition(Wrapper <RentEntity> wrapper) throws Exception {
        return rentMapper.delete(wrapper);
    }

    @Override
    public RentEntity selectByCondition(Wrapper<RentEntity> wrapper) {
        return rentMapper.selectOne(wrapper);
    }
}
