package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.bean.CompanyBean;
import com.cbd.demo.dao.IAdminDao;
import com.cbd.demo.dao.ICompanyDao;
import com.cbd.demo.dao.ITenantryDao;
import com.cbd.demo.entity.AdminEntity;
import com.cbd.demo.entity.CompanyEntity;
import com.cbd.demo.entity.TenantryEntity;
import com.cbd.demo.service.ICompanyService;
import com.cbd.demo.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : CompanyServiceImpl
 * @Date ：2019/6/2 15:49
 * @Desc ：类的介绍：企业用户业务层接口实现类
 * @author：作者：王佳伟
 */
@Service
public class CompanyServiceImpl implements ICompanyService {
    //用户持久层接口
    @Autowired
    private IAdminDao adminDao;
    //企业用户持久层接口
    @Autowired
    private ICompanyDao companyDao;
    //租户合约表
    @Autowired
    private ITenantryDao tenantryDao;

    @Override
    public CompanyBean getCompany(int id) {
        CompanyBean companyBean = null;
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("adminId",id);
            AdminEntity adminEntity=adminDao.selectOne(queryWrapper);
            AdminBean adminBean = new AdminBean();
            //将entity转换为Bean
            BeanUtils.copyProperties(adminEntity,adminBean);
            //获取管理员用户对象
            CompanyEntity companyEntity=companyDao.selectByAdminId(queryWrapper);
            companyBean= new CompanyBean();
            BeanUtils.copyProperties(companyEntity,companyBean);
            //将用户对象放入管理员对象中
            companyBean.setAdminBean(adminBean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return companyBean;
    }

    @Override
    public CompanyBean findById(int id) {
        CompanyBean companyBean = null;
        try {
            //查询管理员用户对象
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("companyId",id);
            CompanyEntity companyEntity= companyDao.selectByAdminId(queryWrapper);
            //查询银用户对象
            QueryWrapper<AdminEntity> wrapper = new QueryWrapper<AdminEntity>();

            wrapper.eq("adminId",companyEntity.getAdminId());
            AdminEntity adminEntity=adminDao.selectOne(wrapper);
            //类型转换
            companyBean = new CompanyBean();
            AdminBean adminBean = new AdminBean();
            BeanUtils.copyProperties(companyEntity,companyBean);
            BeanUtils.copyProperties(adminEntity,adminBean);

            companyBean.setAdminBean(adminBean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return companyBean;
    }

    @Override
    public int removeCompany(int id) {
        int in=0;
        try {
            //根据用户id查找企业用户
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("adminId",id);
            CompanyEntity com= companyDao.selectByAdminId(wrapper);

            //查询条件
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("companyId",com.getCompanyId());
            //查看租户表中该企业的合同,如果有符合情况的合同,则不删除
            IPage<TenantryEntity> page = new Page<TenantryEntity>(1,500);
            IPage<TenantryEntity> list=tenantryDao.showAllTenantry(page,queryWrapper);
            for (TenantryEntity tenantry : list.getRecords()) {
                String status=tenantry.getTenantryContractStatus();
                if(status.equals("已生效")||status.equals("未生效")){
                    return 0;
                }
            }
            //删除企业用户的账号密码
            adminDao.delAdmin(id);
            //条件删除企业用户
            in= companyDao.delCompany(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return  0;
        }

        return in;
    }

    @Override
    public int updateCompany(CompanyBean administrator) {
        int in=0;
        try {
            CompanyEntity companyEntity = new CompanyEntity();
            BeanUtils.copyProperties(administrator,companyEntity);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("companyId",administrator.getCompanyId());
            in=companyDao.updateById(companyEntity,queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return in;
    }

    @Override
    public int saveAdministrator(CompanyBean administrator) {
        int in=0;
        try {
            //创建enetity对象
            CompanyEntity companyEntity = new CompanyEntity();
            AdminEntity adminEntity = new AdminEntity();
            BeanUtils.copyProperties(administrator,companyEntity);
            BeanUtils.copyProperties(administrator.getAdminBean(),adminEntity);

            //添加用户对象
            adminEntity.setAdminRoleType(2);
            adminEntity.setRoleId(6);
            //将加密的密码存入数据库
            adminEntity.setAdminPassword(MD5Utils.md5(adminEntity.getAdminPassword()));
            int in2=adminDao.insert(adminEntity);

            int id = adminEntity.getAdminId();
            companyEntity.setAdminId(id);
            //添加企业用户
            int in1=companyDao.insertCompany(companyEntity);

            //判断是不是都成功了
            in=in1>=1&&in2>=1?1:0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return in;
    }

    @Override
    public IPage<CompanyEntity> listCompany(int currentPage, int pageSize, String name) {
        IPage<CompanyEntity> list = null;
        try {
            //创建分页对象
            Page<CompanyEntity>  pages = new Page<CompanyEntity>(currentPage,pageSize);
            //设置条件
            QueryWrapper<CompanyEntity> queryWrapper = new QueryWrapper<CompanyEntity>();
            if(name!=null){
                queryWrapper.like("companyName",name);
            }
            list=  companyDao.selectPage(pages,queryWrapper);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public IPage<CompanyEntity> listCompanys(int currentPage, int pageSize, CompanyBean company) {
        IPage<CompanyEntity> list = null;
        try {
            //创建分页对象
            Page<CompanyEntity>  pages = new Page<CompanyEntity>(currentPage,pageSize);
            //设置条件
            QueryWrapper<CompanyEntity> queryWrapper = new QueryWrapper<CompanyEntity>();
            if(company!=null){
                queryWrapper.like("companyName",company.getCompanyName());
                queryWrapper.like("companyPosition",company.getCompanyPosition());
                queryWrapper.like("companyContact",company.getCompanyContact());
                queryWrapper.like("companyPhone",company.getCompanyPhone());
            }
            list=  companyDao.selectPage(pages,queryWrapper);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return list;
    }
}
