package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.ITenantryDao;
import com.cbd.demo.entity.TenantryEntity;
import com.cbd.demo.mapper.TenantryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @ClassName : TenantryDaoImpl
 * @Date ：2019/5/31 10:16
 * @Desc ：类的介绍：租户合约持久化接口实现类
 * @author：作者：王佳伟
 */
@Repository
public class TenantryDaoImpl implements ITenantryDao {
    @Autowired
    private TenantryMapper tenantryMapper;
    @Override
    public int addNewTenantry(TenantryEntity tenantryEntity) throws Exception {
        return tenantryMapper.insert(tenantryEntity);
    }

    @Override
    public int updateTenantry(TenantryEntity tenantryEntity, Wrapper<TenantryEntity> wrapper) throws Exception {
        return tenantryMapper.update(tenantryEntity,wrapper);
    }
    @Override
    public IPage<TenantryEntity> showAllTenantry(IPage<TenantryEntity> page, Wrapper<TenantryEntity> wrapper)throws Exception {
        return tenantryMapper.selectPage(page,wrapper);
    }

    @Override
    public TenantryEntity findById(Serializable id)throws Exception {
        return tenantryMapper.selectById(id);
    }

    @Override
    public int getSun(Wrapper<TenantryEntity> wrapper) {
        return tenantryMapper.selectCount(wrapper);
    }

    @Override
    public int getCountMoney(int id) {
        int money=0;
        if(id==-1){
            money=tenantryMapper.getAllCountMoney();
        }else{
            money=tenantryMapper.getCountMoney(id);
        }
        return money;
    }

    @Override
    public TenantryEntity selectByCondition(Wrapper<TenantryEntity> wrapper) {
        return tenantryMapper.selectOne(wrapper);
    }
}
