package com.cbd.demo.dao.impl;

import com.cbd.demo.dao.IRentDao;
import com.cbd.demo.dao.IRoleDao;
import com.cbd.demo.entity.RoleEntity;
import com.cbd.demo.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName : RoleDaoImpl
 * @Date ：2019/5/31 13:42
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 * 陈玉婷增加了异常
 */
@Repository
public class RoleDaoImpl implements IRoleDao {

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public int addRole(RoleEntity roleEntity) throws Exception {
        return roleMapper.addRole(roleEntity);
    }

    @Override
    public RoleEntity getRole(int adminId) throws Exception {
        return roleMapper.getRole(adminId);
    }

    @Override
    public List<RoleEntity> showAllRole() throws Exception {
        return roleMapper.listRoleEntity();
    }

    @Override
    public int addRoleAndPowerId(int roleId, int powerId) {
        return roleMapper.addRoleAndPowerId(roleId,powerId);
    }

    @Override
    public RoleEntity getRoleName(String name) {
        return roleMapper.getRoleName(name);
    }

    @Override
    public int getRoleCount(String name) {
        return roleMapper.getRoleCount(name);
    }

    @Override
    public int delPower(int id) {
        return roleMapper.delPower(id);
    }

}

