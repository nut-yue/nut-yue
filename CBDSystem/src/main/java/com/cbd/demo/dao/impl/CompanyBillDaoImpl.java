package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.ICompanyBillDao;
import com.cbd.demo.entity.CompanyBillEntity;
import com.cbd.demo.mapper.CompanyBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName : CompanyBillDaoImpl
 * @Date ：2019/5/31 10:43
 * @Desc ：类的介绍：
 * @author：岳超
 */
@Repository
public class CompanyBillDaoImpl implements ICompanyBillDao {

    @Autowired
    private CompanyBillMapper companyBillMapper;

    @Override
    public IPage<CompanyBillEntity> listCompanyBill(IPage<CompanyBillEntity> page, Wrapper <CompanyBillEntity> queryWrapper) throws Exception {
        return companyBillMapper.selectPage(page,queryWrapper);
    }

    @Override
    public CompanyBillEntity getCompanyBill(int id) throws Exception {
        return companyBillMapper.selectById(id);
    }

    @Override
    public List<CompanyBillEntity> getCompanyBill(Wrapper<CompanyBillEntity> queryWrapper) throws Exception{
        return companyBillMapper.selectList(queryWrapper);
    }

    @Override
    public int insert(CompanyBillEntity companyBillEntity) {
        return companyBillMapper.insert(companyBillEntity);
    }

    @Override
    public int updateByCondition(CompanyBillEntity companyBillEntity,Wrapper<CompanyBillEntity> wrapper) {
        return companyBillMapper.update(companyBillEntity,wrapper);
    }
}
