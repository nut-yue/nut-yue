package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cbd.demo.dao.IAdminDao;
import com.cbd.demo.entity.AdminEntity;
import com.cbd.demo.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * @ClassName : AdminDaoIpml
 * @Date ：2019/5/31 17:28
 * @Desc ：用户持久接口实现
 * @author：作者：陈云强
 */
@Repository
public class AdminDaoImpl implements IAdminDao {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public AdminEntity selectOne(Wrapper<AdminEntity> queryWrapper) throws Exception {
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateById(AdminEntity admin, Wrapper<AdminEntity> updateWrapper) throws Exception {
        return adminMapper.update(admin,updateWrapper);
    }

    @Override
    public int insert(AdminEntity admin) throws Exception {
        return adminMapper.insert(admin);
    }

    @Override
    public int delAdmin(int id) {
        return adminMapper.deleteById(id);
    }

    @Override
    public int updateRole(int adminId, int roleId) {

        return adminMapper.updateRole(adminId,roleId);
    }
}
