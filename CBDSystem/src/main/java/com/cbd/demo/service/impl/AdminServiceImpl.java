package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.dao.IAdminDao;
import com.cbd.demo.dao.IRoleDao;
import com.cbd.demo.entity.AdminEntity;
import com.cbd.demo.entity.RoleEntity;
import com.cbd.demo.service.IAdminService;
import com.cbd.demo.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : AdminServiceImpl
 * @Date ：2019/6/1 10:30
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private IAdminDao adminDao;
    @Autowired
    private IRoleDao roleDao;
    @Override
    public AdminBean getAdmin(String adminName) {
        AdminBean admin = null;
        try {
            //查询该用户
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("adminName",adminName);
            AdminEntity adminEntity= adminDao.selectOne(queryWrapper);
            //获取角色信息
            int id=  adminEntity.getRoleId();
            RoleEntity roleEntity=roleDao.getRole(id);
            //将entity转换为bean
            admin = new AdminBean();
            BeanUtils.copyProperties(adminEntity,admin);
            admin.setRoleBean(roleEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return admin;
    }

    @Override
    public AdminBean getAdmin(String adminName, String adminPassword) {
        AdminBean admin = null;
        try {
            //查询条件
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("adminName",adminName);
            queryWrapper.eq("adminPassword",adminPassword);

            AdminEntity adminEntity= adminDao.selectOne(queryWrapper);
            admin = new AdminBean();
            //将entity转换为bean
            BeanUtils.copyProperties(adminEntity,admin);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return admin;
    }

    @Override
    public int updateAdmin(int id, String newPwd) {
        int in=0;
        try {
            //加密
            String pwd=MD5Utils.md5(newPwd);
            //条件
            UpdateWrapper wrapper = new UpdateWrapper();
            wrapper.eq("adminId",id);
            wrapper.set(true,"adminPassword",pwd);
            //调用dao层方法
            in=adminDao.updateById(null,wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return in;
    }
}
