package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.dao.ICompanyDao;
import com.cbd.demo.entity.CompanyEntity;
import com.cbd.demo.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @ClassName : CompanyDaoImpl
 * @Date ：2019/5/31/0031 16:48
 * @Desc ：类的介绍：
 * @author：张皓 企业dao实现类
 */
@Repository
public class CompanyDaoImpl implements ICompanyDao {
    @Autowired
    private CompanyMapper companyMapper;


    @Override
    public CompanyEntity selectByAdminId(Wrapper<CompanyEntity> wrapper) throws Exception {
        return companyMapper.selectOne(wrapper);
    }

    @Override
    public int deleteById(Serializable id) throws Exception {
        return companyMapper.deleteById(id);
    }

    @Override
    public int updateById(CompanyEntity companyEntity, Wrapper<CompanyEntity> queryWrapper) throws Exception {
        return companyMapper.update(companyEntity,queryWrapper);
    }

    @Override
    public int insertCompany(CompanyEntity companyEntity) throws Exception {
        return companyMapper.insert(companyEntity);
    }

    @Override
    public IPage<CompanyEntity> selectPage(Page<CompanyEntity> page, Wrapper<CompanyEntity> queryWrapper) throws Exception {
        return companyMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int delCompany(Wrapper<CompanyEntity> wrapper) {
        return companyMapper.delete(wrapper);
    }
}
