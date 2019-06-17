package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.CompanyBillEntity;

import java.util.List;

/**
 * @ClassName : companyBill
 * @Date ：2019/5/30 18:48
 * @Desc ：类的介绍：企业计费账单持久化接口
 * @author：作者：王佳伟
 */
public interface ICompanyBillDao {
    /**
     * 按条件分页查询
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return 分页封装对象
     */
    IPage<CompanyBillEntity> listCompanyBill(IPage<CompanyBillEntity> page, Wrapper<CompanyBillEntity> queryWrapper) throws Exception;
    /**
     * 根据企业计费账单id查找该企业计费账单对象
     * @param id 企业id
     * @return 企业计费账单的对象
     */
    CompanyBillEntity getCompanyBill(int id) throws Exception;
    /**
     * 按条件查询
     * @param queryWrapper 条件构造器
     * @return 对象集合
     */
    List<CompanyBillEntity> getCompanyBill(Wrapper<CompanyBillEntity> queryWrapper) throws Exception;

    /**
     * 新增企业账单计费
     * @param companyBillEntity 计费账单对象
     * @return 添加成功条数
     */
    int insert (CompanyBillEntity companyBillEntity);

    /**
     * 按条件修改
     * @param wrapper 条件构造器
     * @param companyBillEntity 账单对象
     * @return 修改成功条数
     */
    int updateByCondition(CompanyBillEntity companyBillEntity,Wrapper<CompanyBillEntity>wrapper);
}
