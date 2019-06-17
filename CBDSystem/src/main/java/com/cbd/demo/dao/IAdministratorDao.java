package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.cbd.demo.entity.AdministratorEntity;

import java.io.Serializable;


/**
 * @ClassName : IAdministratorDao
 * @Date ：2019/5/28 15:45
 * @Desc ：类的介绍：管理员持久化接口
 * @author：作者：峗权
 */
public interface IAdministratorDao {
    /**
     * 根据用户id查询管理员对象
     * @param adminId 用户id
     * @return 管理员实体类
     */
    AdministratorEntity getByAdminId(Serializable adminId)throws Exception;

    /**
     * 条件查询
     * @param queryWrapper 条件构造器
     * @return 用户管理员
     */
    AdministratorEntity selectOne(Wrapper<AdministratorEntity> queryWrapper) throws Exception;

    /**
     * 分页查询
     * @param page 分页条件对象
     * @return 类型IPage<AdministratorEntity> 分页封装对象
     */
    IPage<AdministratorEntity> selectPage(IPage<AdministratorEntity> page)throws Exception;

    /**
     * 修改管理员信息
     * @param administrator 管理员实体类
     * @return 修改的条数
     */
    int updateAdministrator(AdministratorEntity administrator)throws Exception;

    /**
     * 添加后台管理员
     * @param administrator 管理员实体类
     * @return 添加的条数
     */
    int addAdministrator(AdministratorEntity administrator)throws Exception;

    /**
     * 根据管理员用户id删除管理员对象
     * @param id 管理员id
     * @return 是否成功，失败返回0
     */
     int delAdministrator(Serializable id);
}
