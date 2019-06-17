package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cbd.demo.dao.ITransactionDao;
import com.cbd.demo.entity.TransactionEntity;
import com.cbd.demo.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Wrapper;

/**
 * @ClassName : TransactionDaoImpl
 * @Date ：2019/5/31 10:42
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@Repository
public class TransactionDaoImpl implements ITransactionDao {
    @Autowired
    private TransactionMapper transactionMapper;
    @Override
    public int saveTransaction(TransactionEntity transaction) throws Exception {
        return transactionMapper.insert(transaction);
    }

    @Override 
    public TransactionEntity getTransaction(Serializable id) throws Exception {
        return transactionMapper.selectById(id);
    }

    @Override
    public TransactionEntity getByCondition(QueryWrapper<TransactionEntity> wrapper) throws Exception {
        return transactionMapper.selectOne(wrapper);
    }
}
