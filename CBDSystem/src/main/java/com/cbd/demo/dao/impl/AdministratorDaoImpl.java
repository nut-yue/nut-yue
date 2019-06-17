package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.IAdministratorDao;
import com.cbd.demo.entity.AdministratorEntity;
import com.cbd.demo.mapper.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @ClassName : AdministratorDaoImpl
 * @Date ：2019/5/31 14:16
 * @Desc ：类的介绍：IAdministratorDao持久层接口实现类
 * @author：作者：峗权
 */

@Repository
public class AdministratorDaoImpl implements IAdministratorDao {
    @Autowired
    private AdministratorMapper administratorMapper;

    @Override
    public AdministratorEntity getByAdminId(Serializable adminId) throws Exception {
        return administratorMapper.selectById(adminId);
    }

    @Override
    public AdministratorEntity selectOne(Wrapper<AdministratorEntity> queryWrapper) throws Exception {
        return administratorMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<AdministratorEntity> selectPage(IPage<AdministratorEntity> page) throws Exception {
        return administratorMapper.selectPage(page,null);
    }

    @Override
    public int updateAdministrator(AdministratorEntity administrator) throws Exception {
        return administratorMapper.updateById(administrator);
    }

    @Override
    public int addAdministrator(AdministratorEntity administrator) throws Exception {
        return administratorMapper.insert(administrator);
    }

    @Override
    public int delAdministrator(Serializable id) {
        return administratorMapper.deleteById(id);
    }
}
