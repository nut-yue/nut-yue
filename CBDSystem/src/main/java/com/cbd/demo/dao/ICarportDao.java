package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.CarportEntity;
import com.cbd.demo.entity.UserEntity;

import java.io.Serializable;

/**
 * @ClassName : ICarportService
 * @Date ：2019/5/28 15:37
 * @Desc ：接口的介绍： 个人车位dao接口
 * @author：作者： 王佳伟
 * @change 峗权 （添加了抛出异常、增加了删除方法）
 */
public interface ICarportDao {
    /**
     * 按id修改记录,要修改什么属性设置什么属性，
     * @param carport 对象实例
     * @param updateWrapper 条件构造器
     * @return 操作记录条数
     */
    int updateByWrapper(CarportEntity carport,Wrapper<CarportEntity> updateWrapper) throws Exception;

    /**
     * 按照主键id查询信息
     * @param id 车位id
     * @return 车位对象
     */
    CarportEntity getCarport(Serializable id) throws Exception;

    /**
     * 添加车位
     * @param carport 车位对象
     * @return 影响行数
     */
    int inserCarport(CarportEntity carport) throws Exception;
    /**
     * 条件分页查询
     * @param page  分页对象
     * @param wrapper 条件构造器
     * @return 分页对象
     */
    IPage<CarportEntity> listCarport(IPage<CarportEntity> page, Wrapper<CarportEntity> wrapper) throws Exception;

    /**
     * 根据车位ID删除对应车位
     * @param carportId 车位ID
     * @return 删除的条数
     */
    int deleteCarport(Serializable carportId) throws Exception;

}
