package com.cbd.demo.service;

import com.cbd.demo.entity.RoleEntity;

import java.util.List;

/**
 * @ClassName : IRoleService
 * @Date ：2019/5/28 15:28
 * @Desc ：类的介绍：角色业务接口
 * @author：作者：刘划轩
 */
public interface IRoleService {
    /**
     * 创建新的角色
     * @param roleEntity 角色对象
     */
    int addRole(RoleEntity roleEntity);

    /**
     * 创建用户时，查询所有的角色对象
     * @return 用户对象集合
     */
    List<RoleEntity> showAllRole();

    /**
     * 根据角色名查看角色名是否重复
     * @param name 角色名字
     * @return 返回0则是成功，返回-1则是异常
     */
    int getRoleCount(String name);

    /**
     * 修改角色权限
     * @param role 角色对象
     * @return
     */
    int updateRole(RoleEntity role);

    /**
     * 根据用户id查看该用户的角色和权限信息
     * @param id 用户id
     * @return
     */
    RoleEntity getRole(int id);
}
