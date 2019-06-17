package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.IPersonalDao;
import com.cbd.demo.entity.PersonalEntity;
import com.cbd.demo.mapper.PersonalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @ClassName : PersonalDaoImpl
 * @Date ：2019/5/31/0031 15:14
 * @Desc ：类的介绍：个人买卖车合同持久层实现类
 * @author：张皓
 */
@Repository
public class PersonalDaoImpl implements IPersonalDao {
    @Autowired
    private PersonalMapper personalMapper;
    @Override
    public int insert(PersonalEntity personalEntity) throws Exception {
        return personalMapper.insert(personalEntity);
    }

    @Override
    public int update(PersonalEntity personalEntity, Wrapper<PersonalEntity> updateWrapper) throws Exception {
        return personalMapper.update(personalEntity,updateWrapper);
    }

    @Override
    public PersonalEntity selectById(Serializable personalId) throws Exception {
        return personalMapper.selectById(personalId);
    }

    @Override
    public IPage<PersonalEntity> selectPage(IPage<PersonalEntity> page, Wrapper<PersonalEntity> queryWrapper) throws Exception {
        return personalMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int deleteById(int personalId) {
        return personalMapper.deleteById(personalId);
    }
}
