package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.RentEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @InterfaceName : IRentDao
 * @Date ：2019/5/31 10:30
 * @Desc ：接口的介绍： 出租信息持久接口
 * @author：岳超
 */
public interface IRentDao {

    /**
     * 添加租借信息记录
     * @param rentEntity 租借展示信息对象
     * @return 操作记录条数
     */
    int insert(RentEntity rentEntity) throws Exception;

    /**
     * 按租借信息id查询租借信息查询
     * @param rentId 主键id
     * @return 对象实例
     */
    RentEntity selectById(Serializable rentId) throws Exception;

    /**
     * 按条件分页租借信息
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return 租借信息分页封装对象
     */
    IPage<RentEntity> selectPage(IPage<RentEntity> page, Wrapper<RentEntity> queryWrapper) throws Exception;

    /**
     * 按车位id删除车位展示记录
     * @param wrapper  条件构造器
     * @return 操作成功条数
     */
    int deleteByCondition(Wrapper<RentEntity> wrapper) throws Exception;

    /**
     * 根据条件查询车位出租信息
     * @param wrapper 条件构造器
     * @return 车位出租信息对象
     */
    RentEntity selectByCondition(Wrapper<RentEntity> wrapper);

}
