package com.cbd.demo.dao;/**
 * Created by 123 on 2019/5/28.
 */

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.ExternalEntity;

import java.io.Serializable;


/**
 * @ClassName : IExternalService
 * @Date ： 2019年5月31日10:39:34
 * @Desc ：接口的介绍：外部合约的dao接口
 * @author：作者： 胡平
 */
public interface IExternalDao {

    /**
     * 添加记录
     * @param externalEntity 对象实例
     * @return 操作记录条数
     */
    int insert(ExternalEntity externalEntity) throws Exception;


    /**
     * 条件修改
     * @param entity 对象实例
     * @return 操作记录条数
     */
    int updateExternal(ExternalEntity entity, Wrapper<ExternalEntity> queryWrapper) throws Exception;

    /**
     * 带条件的分页查询
     * @param ipage 分页参数
     * @param queryWrapper 条件
     * @return 分页对象
     */
    IPage<ExternalEntity> listExternal(IPage<ExternalEntity> ipage,
                                       Wrapper<ExternalEntity> queryWrapper) throws Exception;

    /**
     * 通过id查询
     * @param id id
     * @return 外部合约实体类
     */
    ExternalEntity getById(Serializable id) throws Exception;

    /**
     * 统计金额
     * @param
     * @return
     */
    public int getgetCountMoney();
    /**
     * 统计合同数量
     * @return
     */
    int getConut();

    /**
     * 查询最后添加的ID
     * @return 外部合约ID
     */
    int getInsertId();

}
