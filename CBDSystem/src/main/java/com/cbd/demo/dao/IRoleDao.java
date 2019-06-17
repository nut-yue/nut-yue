package com.cbd.demo.dao;

import com.cbd.demo.entity.RoleEntity;

import java.util.List;

/**
 * @ClassName : IRoleService
 * @Date ：2019/5/28 15:28
 * @Desc ：类的介绍：角色持久化接口
 * @author：作者：王佳伟
 * 陈玉婷增加了异常
 */
public interface IRoleDao {
    /**
     * 创建新的角色
     * @param roleEntity 角色对象
     */
    int addRole(RoleEntity roleEntity) throws Exception;

    /**
     * 根据角色id查找角色对象，同时再查询该角色的权限信息
     * @param id 角色id
     * @return 角色
     */
    RoleEntity getRole(int id) throws Exception;
    /**
     * 创建用户时，查询所有的角色对象
     * @return 用户对象集合
     */
    List<RoleEntity> showAllRole()throws Exception;

    /**
     * 根据角色id和权限id增加角色的权限
     * @param roleId 角色id
     * @param powerId 权限id
     * @return 是否成功。成功返回1，失败返回0
     */
    int addRoleAndPowerId(int roleId,int powerId) throws Exception;
    /**
     * 根据角色名字获取角色
     */
    RoleEntity getRoleName(String name)throws Exception;

    /**
     * 查看用户名是否重复
     * @param name 用户名
     * @return 重复的个数，如果是0，则不重复。
     */
    int getRoleCount(String name) throws Exception;

    /**
     * 根据角色id删除权限中间表的内容
     * @param id 角色id
     * @return 是否成功
     */
    int delPower(int id);
}
