package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.IExternalDao;
import com.cbd.demo.entity.ExternalEntity;
import com.cbd.demo.mapper.ExternalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @ClassName : ExternalDaoImpl
 * @Date ：2019/5/31 10:22
 * @Desc ：类的介绍 外部合约的dao
 * @author：作者：胡平
 */
@Repository
public class ExternalDaoImpl implements IExternalDao{

    @Autowired
    private ExternalMapper externalMapper;

    @Override
    public int insert(ExternalEntity externalEntity) throws Exception {
        return externalMapper.insert(externalEntity);
    }

    @Override
    public int updateExternal(ExternalEntity entity,
                          Wrapper<ExternalEntity> queryWrapper) throws Exception {
        return externalMapper.update(entity, queryWrapper);
    }

    @Override
    public IPage<ExternalEntity> listExternal(IPage<ExternalEntity> ipage,
                                              Wrapper<ExternalEntity> queryWrapper) throws Exception {
        return externalMapper.selectPage(ipage, queryWrapper);
    }

    @Override
    public ExternalEntity getById(Serializable id) throws Exception {
        return externalMapper.selectById(id);
    }

    @Override
    public int getConut() {
        return  externalMapper.selectCount(null);
    }

    @Override
    public int getInsertId() {
        return externalMapper.getInsertId();
    }

    @Override
    public int getgetCountMoney( ){
        int money=0;
        money=externalMapper.getAllCountMoney();
        return money;
    }
}
