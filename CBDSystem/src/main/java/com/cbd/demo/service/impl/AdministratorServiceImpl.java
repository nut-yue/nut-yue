package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.bean.AdministratorBean;
import com.cbd.demo.dao.IAdminDao;
import com.cbd.demo.dao.IAdministratorDao;
import com.cbd.demo.dao.IPowerDao;
import com.cbd.demo.dao.IRoleDao;
import com.cbd.demo.entity.AdminEntity;
import com.cbd.demo.entity.AdministratorEntity;
import com.cbd.demo.entity.PowerEntity;
import com.cbd.demo.entity.RoleEntity;
import com.cbd.demo.service.IAdministratorService;
import com.cbd.demo.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : AdministratorServiceImpl
 * @Date ：2019/6/2 14:23
 * @Desc ：类的介绍：管理员用户业务接口实现类
 * @author：作者：王佳伟
 */
@Service
public class AdministratorServiceImpl implements IAdministratorService {
    //用户持久层接口
    @Autowired
    private IAdminDao adminDao;
    //管理员持久化接口
    @Autowired
    private IAdministratorDao administratorDao;
    //角色持久化接口
    @Autowired
    private IRoleDao roleDao;
    //权限持久化接口
    @Autowired
    private IPowerDao powerDao;
    @Override
    public AdministratorBean getAdministrator(int id) {
        AdministratorBean administratorBean=null;
        try {
            //获得用户entity
            AdminBean adminBean = new AdminBean();
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("adminId",id);
            AdminEntity adminEntity=adminDao.selectOne(queryWrapper);
            //将entity对象转换为bean对象
            BeanUtils.copyProperties(adminEntity,adminBean);
            //查找管理员用户
            administratorBean = new AdministratorBean();
            AdministratorEntity administratorEntity=administratorDao.selectOne(queryWrapper);
            BeanUtils.copyProperties(administratorEntity,administratorBean);
            //将用户信息添加到管理员对象中
            administratorBean.setAdminBean(adminBean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return administratorBean;
    }

    @Override
    public AdministratorBean findById(int id) {
        AdministratorBean administratorBean=null;
        try {
            AdministratorEntity administratorEntity=administratorDao.getByAdminId(id);
            //将entity对象转换为bean对象
            administratorBean = new AdministratorBean();
            BeanUtils.copyProperties(administratorEntity,administratorBean);

            //同时查找用户，封装到属性中
            Integer in= administratorEntity.getAdminId();
            //设置条件
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("adminId",in);
            //获取用户entity
            AdminEntity adminEntity=adminDao.selectOne(queryWrapper);
            //将entity对象转换为bean对象
            AdminBean adminBean = new AdminBean();
            BeanUtils.copyProperties(adminEntity,adminBean);
            administratorBean.setAdminBean(adminBean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return administratorBean;
    }

    @Override
    public IPage<AdministratorEntity> listAdministrator(int currentPage, int pageSize) {
        IPage<AdministratorEntity> page =null;
        try {
            //创建分页对象
            IPage<AdministratorEntity>  pages = new Page<AdministratorEntity>(currentPage,pageSize);
             page= administratorDao.selectPage(pages);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return page;
    }

    @Override
    public int updateAdministrator(AdministratorBean administrator) {
        int in = 0;
        try {
            AdministratorEntity administratorEntity = new AdministratorEntity();
            //将bean转换为entity
            BeanUtils.copyProperties(administrator,administratorEntity);
            in=administratorDao.updateAdministrator(administratorEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return in;
    }

    @Override
    public int insertAdministrator(AdministratorBean administratorBean) {
        int in = 0;
        try {
            //bean类型
            AdminBean adminBean = administratorBean.getAdminBean();
            //entity类型
            AdministratorEntity administratorEntity = new AdministratorEntity();
            AdminEntity adminEntity = new AdminEntity();
            //将bean转换为entity
            BeanUtils.copyProperties(adminBean,adminEntity);
            BeanUtils.copyProperties(administratorBean,administratorEntity);

            //创建角色
            RoleEntity roleEntity = new RoleEntity();
            //用户账号做用户名
            roleEntity.setRoleName(administratorBean.getAdminBean().getAdminName());
            roleEntity.setName("后台管理员");
            roleDao.addRole(roleEntity);
            //获取添加角色的id
            Integer integer=roleEntity.getRoleId();


            //给管理员权限
            roleDao.addRoleAndPowerId(integer,8);
            //遍历传过来的权限,给该角色添加权限
            for (int i = 0; i <administratorBean.getType().length; i++) {
                 String str =administratorBean.getType()[i];
                //4合约管理：contract、5车位管理：stall、6用户管理：personage、7投诉管理：complaints
                 switch (str){
                     case "合约管理":
                        roleDao.addRoleAndPowerId(integer,4);
                         break;
                     case "车位管理":
                         roleDao.addRoleAndPowerId(integer,5);
                        break;
                     case "用户管理":
                         roleDao.addRoleAndPowerId(integer,6);
                        break;
                     case "投诉管理":
                         roleDao.addRoleAndPowerId(integer,7);
                        break;
                 }
            }
            //设置用户类型
            adminEntity.setAdminRoleType(3);
            //将加密的密码存入数据库
            adminEntity.setAdminPassword(MD5Utils.md5(adminEntity.getAdminPassword()));
            //将角色id添加到用户对象中
            adminEntity.setRoleId(integer);
            //添加用户
            int in2= adminDao.insert(adminEntity);
            //获取用户id
            int id=adminEntity.getAdminId();
            //将用户id添加到管理员对象中
            administratorEntity.setAdminId(id);
            //添加管理员用户是否成功
            int in1= administratorDao.addAdministrator(administratorEntity);
            //判断是不是都成功了
            in=in1>=1&&in2>=1?1:0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return in;
    }

    public int delAdministrator(int id){
        int in=0;
        try {
            AdministratorEntity administrator= administratorDao.getByAdminId(id);
            if(administrator!=null&&administrator.getAdministratorId()!=1){
                //删除与之对应的用户名和密码
                adminDao.delAdmin(administrator.getAdminId());
                //删除管理员
                in=administratorDao.delAdministrator(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return in;
    }

    @Override
    public int updateRole(AdministratorBean administratorBean) {
        int in=0;
        try {
            //获得管理员
            AdministratorEntity administrator=administratorDao.getByAdminId(administratorBean.getAdministratorId());
            //修改管理员的权限
            if(administrator!=null){
                //管理员的用户信息
                QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("adminId",administrator.getAdminId());
                //获取角色id
                int id = adminDao.selectOne(queryWrapper).getRoleId();
                //根据角色id删除对应权限
                roleDao.delPower(id);

                //给管理员权限
                roleDao.addRoleAndPowerId(id,8);
                //遍历传过来的权限,给该角色添加权限
                for (int i = 0; i <administratorBean.getType().length; i++) {
                    String str =administratorBean.getType()[i];
                    //4合约管理：contract、5车位管理：stall、6用户管理：personage、7投诉管理：complaints
                    switch (str){
                        case "合约管理":
                            roleDao.addRoleAndPowerId(id,4);
                            break;
                        case "车位管理":
                            roleDao.addRoleAndPowerId(id,5);
                            break;
                        case "用户管理":
                            roleDao.addRoleAndPowerId(id,6);
                            break;
                        case "投诉管理":
                            roleDao.addRoleAndPowerId(id,7);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
