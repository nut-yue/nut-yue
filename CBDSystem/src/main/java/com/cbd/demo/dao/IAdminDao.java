package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cbd.demo.entity.AdminEntity;

/**
 * @ClassName : AdminService
 * @Date ：2019/5/31 10:37
 * @Desc ：类的介绍：用户持久化接口
 * @author：作者：杨强
 */
public interface IAdminDao {

    /**
     * 多条件查询用户对象
     * @return
     */
    AdminEntity selectOne(Wrapper<AdminEntity> queryWrapper) throws Exception;
    /**
     * 修改个人密码或者个人信息
     * @return 返回是否成功，失败返回0 成功返回1
     */
    int updateById(AdminEntity admin, Wrapper<AdminEntity> updateWrapper) throws Exception;


    /**
     * 添加用户
     * @param admin
     * @return
     */
    int insert(AdminEntity admin) throws Exception;

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    int delAdmin(int id);

    /**
     * 修改管理员的权限
     * @param adminId 管理员id
     * @param roleId 角色id
     * @return 是否成功
     */
    int updateRole(int adminId,int roleId);
}
