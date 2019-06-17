package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cbd.demo.dao.IAdminDao;
import com.cbd.demo.dao.IAdministratorDao;
import com.cbd.demo.dao.IRoleDao;
import com.cbd.demo.entity.AdminEntity;
import com.cbd.demo.entity.PowerEntity;
import com.cbd.demo.entity.RoleEntity;
import com.cbd.demo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : RoleServiceImpl
 * @Date ：2019/6/3 12:40
 * @Desc ：类的介绍：角色实现类
 * @author：作者：陈玉婷
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;
    @Autowired
    private IAdministratorDao administratorDao;
    @Autowired
    private IAdminDao adminDao;
    @Override
    public int addRole(RoleEntity roleEntity) {
        int result = 0;
        try {
            //获取权限集合
            List<PowerEntity> list=roleEntity.getPower();
            result = roleDao.addRole(roleEntity);
            //获取添加角色的id
            String name = roleEntity.getRoleName();
            RoleEntity role=roleDao.getRoleName(name);
            int resultId=role.getRoleId();
            //添加权限
            for (PowerEntity powerEntity : list) {
                    roleDao.addRoleAndPowerId(resultId,powerEntity.getPowerId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return result;
    }

    @Override
    public List<RoleEntity> showAllRole() {
        List<RoleEntity> roleEntities= null;
        try {
            roleEntities = roleDao.showAllRole();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleEntities;
    }

    @Override
    public int getRoleCount(String name) {
        int in=0;
        try {
            in=roleDao.getRoleCount(name);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return in;
    }

    @Override
    public int updateRole(RoleEntity role) {
        int in = 0;
        try {
        //获取新的权限
        List<PowerEntity> list= role.getPower();
        //删除旧的权限
        in= roleDao.delPower(role.getRoleId());
        //添加新的权限
        for (PowerEntity power : list) {
                roleDao.addRoleAndPowerId(role.getRoleId(),power.getPowerId());
        }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return in;
    }

    @Override
    public RoleEntity getRole(int id) {
        RoleEntity role=null;
        try {
            //获得用户
            QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("adminId",id);
            AdminEntity adminEntity=  adminDao.selectOne(queryWrapper);
            //获得角色信息和权限信息
             role=  roleDao.getRole(adminEntity.getRoleId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
}
