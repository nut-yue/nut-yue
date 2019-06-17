package com.cbd.demo.dao;/**
 * Created by 123 on 2019/5/28.
 */

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.TenantryEntity;

import java.io.Serializable;

/**
 * @ClassName : ITenantryService
 * @Date ：2019/5/28 15:29
 * @Desc ：接口的介绍：租户合约的dao接口
 * @author：作者： 王佳伟
 */
public interface ITenantryDao {
    /**
     * 新增租户合同
     * @param tenantryEntity 租户合同对象
     */
    int addNewTenantry(TenantryEntity tenantryEntity) throws Exception;
    /**
     * 租户合同解约，修改合同状态为解约
     * @param tenantryEntity 合同对象，只需要设置合同状态字段为“解约”和主键字段
     * @return
     */
    int updateTenantry(TenantryEntity tenantryEntity,Wrapper<TenantryEntity> wrapper)throws Exception;

    /**
     * 动态条件查询租户合同，返回租户合同集合
     * @param wrapper 条件构造器
     * @return
     */
    IPage<TenantryEntity> showAllTenantry(IPage<TenantryEntity> page,Wrapper<TenantryEntity> wrapper)throws Exception;

    /**
     * 通过租户合同ID查询租户合同详细信息
     * @param id 租户合同ID
     * @return 租户合同实体对象
     */
    TenantryEntity findById(Serializable id)throws Exception;

    /**
     * 根据条件查询该租户有多少合同
     * @param wrapper 条件构造器
     * @return 合同数量
     */
    int getSun(Wrapper<TenantryEntity> wrapper);

    /**
     * 根据租户id查询该租户的金额总量
     * @param id 企业id
     * @return 总金额
     */
    int getCountMoney(int id);

    /**
     * 根据条件查询租户合约对象
     * @param wrapper 条件构造器
     * @return 租户合约对象
     */
    TenantryEntity selectByCondition(Wrapper<TenantryEntity>wrapper) throws Exception;
}
