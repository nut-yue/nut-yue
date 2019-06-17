package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.SaleBean;
import com.cbd.demo.entity.RentEntity;
import com.cbd.demo.entity.SaleEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : ISaleDao
 * @Date ：2019/5/31 10:24
 * @Desc ：接口的介绍： 出售信息dao接口
 * @author：作者：张皓
 */
public interface ISaleDao {
    /**
     * 条件查询所有出售车位信息：条件null为查询所有
     * @param queryWrapper 条件构造器
     * @return 车位信息分页集合
     */
    IPage<SaleEntity> findAllSaleInfo(IPage<SaleEntity> page, Wrapper<SaleEntity> queryWrapper) throws Exception;

    /**
     * 条件查询车位出售信息
     * @param queryWrapper 条件构造器
     * @return
     */
    SaleEntity selectSale(Wrapper<SaleEntity> queryWrapper);
    /**
     * 根据出售信息id调用出售车位信息dao层具体的出售信息
     * @param saleId 出售信息ID
     * @return 信息实体bean
     */
    SaleEntity findBySaleId(Serializable saleId) throws Exception;
    /**
     * 增加出售信息
     * @param saleEntity 出售信息实体类
     * @return 成功1，失败0
     */
    int addSale(SaleEntity saleEntity) throws Exception;

    /**
     * 根据出售信息ID删除出售信息
     * @param saleId 出售信息ID
     * @return 成功1，失败0
     */
    int delSale(Serializable saleId) throws Exception;

    /**
     * 按条件删除出售信息
     * @param wrapper 条件构造器
     * @return 操作成功条数
     * @throws Exception
     */
    int delByCondition(Wrapper<SaleEntity> wrapper) throws Exception;
}
