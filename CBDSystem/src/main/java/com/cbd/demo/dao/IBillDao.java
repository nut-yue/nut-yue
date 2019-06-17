package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.BillEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @InterfaceName : IBillDao
 * @Date ：2019/5/28 15:24
 * @Desc ：接口的介绍： 计费账单持久接口
 * @author：杨强
 */
public interface IBillDao {
    /**
     * 条件分页查询
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return 分页封装对象
     */
    IPage<BillEntity> selectPage(IPage<BillEntity> page, Wrapper<BillEntity> queryWrapper) throws Exception;

    /**
     * 条件查询,用于封装总金额,交易条数等对象
     * @return 集合对象
     */
    List<BillEntity> selectList(Wrapper<BillEntity> queryWrapper) throws Exception;

    /**
     * 条件查询当个用户
     * @param queryWrapper 条件构造器
     * @return 记录对象
     */
    BillEntity selectOne(Wrapper<BillEntity> queryWrapper) throws Exception;

    /**
     * 添加账单记录
     * @param bill 账单对象
     * @return 1表示成功 0表示失败
     */
    int insert(BillEntity bill) throws Exception;
}
