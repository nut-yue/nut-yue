package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.AdministratorBean;
import com.cbd.demo.entity.AdministratorEntity;

/**
 * @ClassName : IAdministratorService
 * @Date ：2019/5/28 18:05
 * @Desc ：类的介绍：管理员用户业务接口
 * @author：作者：王佳伟
 */
public interface IAdministratorService {
    /**
     * 根据用户id查询管理员详细信息
     * @param id 用户id
     * @return 管理员用户对象
     */
    AdministratorBean getAdministrator(int id);
    /**
     * 根据管理员用户id查询管理员用户详细信息
     * @param id 用户id
     * @return 管理员用户对象
     */
    AdministratorBean findById(int id);

    /**
     * 查询所有管理员
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return 管理员分页对象
     */
    IPage<AdministratorEntity> listAdministrator(int currentPage, int pageSize);

    /**
     * 修改管理员信息
     * @param administrator 管理员对象
     * @return 修改成功条数
     */
    int updateAdministrator(AdministratorBean administrator);

    /**
     * 添加后台管理员，同时根据管理员Bean中的adminBean属性添加用户表的数据。并将adminBean中的角色类型设置为3.
     * @param administratorBean 管理员对象
     * @return 添加成功条数
     */
    int insertAdministrator(AdministratorBean administratorBean);

    /**
     * 根据管理员id删除管理员对象
     * @param id
     * @return
     */
     int delAdministrator(int id);

    /**
     * 修改管理员的权限
     * @param administratorBean
     * @return
     */
     int updateRole(AdministratorBean administratorBean);
}
