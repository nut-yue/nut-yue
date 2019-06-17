package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.CompanyBean;
import com.cbd.demo.entity.CompanyEntity;

/**
 * @ClassName : ICompanyService
 * @Date ：2019/5/28 18:27
 * @Desc ：类的介绍：企业用户业务接口
 * @author：作者：王佳伟
 */
public interface ICompanyService {
    /**
     * 根据用户id查询企业用户详细信息
     * @param id 用户id
     * @return 企业用户对象
     */
    CompanyBean getCompany(int id);
    /**
     * 根据企业用户id查询企业用户详细信息
     * @param id 用户id
     * @return 企业用户对象
     */
    CompanyBean findById(int id);
    /**
     * 通过用户id删除企业用户，同时删除用户表中的该记录，
     * 删除之前，需要查看该企业全部合同的合同状态为“已生效”或者“未生效”，
     * 如果有合同，则删除失败。
     * @param id  用户id
     * @return 是否删除成功，0是失败，1是成功
     */
    int removeCompany(int id);

    /**
     * 根据企业实体修改企业信息。
     * @param administrator 企业对象
     * @return 是否删除成功，0是失败，1是成功
     */
    int updateCompany(CompanyBean administrator);

    /**
     * 添加企业用户信息，同时根据企业Bean中的adminBean属性添加用户表的数据。并将adminBean中的角色类型设置为2.
     * @param administrator 企业用户信息
     * @return 是否删除成功，0是失败，1是成功
     */
    int saveAdministrator(CompanyBean administrator);

    /**
     * 分页查询企业用户的信息，如果name不为null，则进行条件查询
     * @param currentPage 当前页
     * @param pageSize 总条数
     *  @param name 企业用户真实姓名
     * @return 企业用户对象的分页对象
     */
    IPage<CompanyEntity> listCompany(int currentPage, int pageSize, String name);
    /**
     * 分页查询企业用户的信息，如果name不为null，则进行条件查询
     * @param currentPage 当前页
     * @param pageSize 总条数
     *  @param name 企业用户真实姓名
     * @return 企业用户对象的分页对象
     */
    IPage<CompanyEntity> listCompanys(int currentPage, int pageSize,CompanyBean company);
}
