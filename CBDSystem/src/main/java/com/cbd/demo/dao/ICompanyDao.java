package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.CompanyEntity;

import java.io.Serializable;

/**
 * @ClassName : ICompanyDao
 * @Date ：2019/5/31 10:38
 * @Desc ：类的介绍：企业用户持久化接口
 * @author：作者：周陆成
 */
public interface ICompanyDao {
    /**
     * 根据用户id查询企业用户详细信息
     * @param queryWrapper 条件构造器，用户id
     * @return 企业用户对象
     */
    CompanyEntity selectByAdminId(Wrapper<CompanyEntity> queryWrapper)throws Exception;

    /**
     * 通过用户id删除企业用户，同时删除用户表中的该记录，
     * @param id  用户id
     * @return 是否删除成功，0是失败，1是成功
     */
    int deleteById(Serializable id)throws Exception;

    /**
     * 根据企业实体修改企业信息。
     * @param companyEntity 企业实体对象
     * @return 是否删除成功，0是失败，1是成功
     */
    int updateById(CompanyEntity companyEntity, Wrapper<CompanyEntity> queryWrapper)throws Exception;

    /**
     * 添加企业用户信息
     * @param companyEntity 企业用户信息
     * @return 是否删除成功，0是失败，1是成功
     */
    int insertCompany(CompanyEntity companyEntity)throws Exception;

    /**
     * 分页查询企业用户的信息，如果name不为null，则进行条件查询
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器 条件为name
     * @return 企业分页对象
     */
    IPage<CompanyEntity> selectPage(Page<CompanyEntity> page, Wrapper<CompanyEntity> queryWrapper)throws Exception;

    /**
     * 删除企业用户
     * @param wrapper
     * @return
     */
    int delCompany(Wrapper<CompanyEntity> wrapper);
}
