package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cbd.demo.entity.TransactionEntity;
import com.cbd.demo.entity.UserEntity;

import java.io.Serializable;
import java.sql.Wrapper;

/**
 * @ClassName : TransactionService
 * @Date ：2019/5/28 15:31
 * @Desc ：类的介绍：出租交易记录持久化接口
 * @author：作者：王佳伟
 */
public interface ITransactionDao {
    /**
     * 添加出租交易记录
     * @param transaction 对象实例
     * @return 操作记录条数
     */
    int saveTransaction(TransactionEntity transaction) throws Exception;

    /**
     * 根据出租交易记录id查询详细信息
     * @param id 出租交易记录id
     * @return 出租交易记录对象
     */
    TransactionEntity getTransaction(Serializable id) throws Exception;


    /**
     * 根据车位信息查看交易记录
     * @param carportId 车位id
     * @return 交易记录
     * @throws Exception
     */
    TransactionEntity getByCondition(QueryWrapper <TransactionEntity> wrapper) throws Exception;

}
