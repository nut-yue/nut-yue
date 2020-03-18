package com.mall.clinicmall_db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.clinicmall_db.entity.AdminEntity;
import com.mall.clinicmall_db.mapper.AdminMapper;
import com.mall.clinicmall_db.service.AdminService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.MapUtils;


import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName : AdminServiceImpl
 * @Date ：2020/3/14 22:06
 * @author：nut-yue
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public AdminEntity getAdmin(String id) {
        return adminMapper.selectById(id);
    }

    @Override
    public AdminEntity getAdmin(Map<String, Object> condition) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<AdminEntity>();
        queryWrapper.allEq(condition);
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<AdminEntity> listAdmin(Map<String,Object> condition) {
        // 封装分页信息
        int currentPage = MapUtils.getInteger(condition,"currentPage");
        int pageSize = MapUtils.getInteger(condition,"PageSize");
        Page<AdminEntity> page = new Page<AdminEntity>(currentPage,pageSize);
        // 查询list集合
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<AdminEntity>();
        queryWrapper.allEq(condition);
        return adminMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int deleteAdmin(String id) {
        return adminMapper.deleteById(id);
    }

    @Override
    public int insertAdmin(AdminEntity adminEntity) {
        // 表主键使用uuid
        adminEntity.setId(UUID.randomUUID().toString());
        return adminMapper.insert(adminEntity);
    }

    @Override
    public int updateAdmin(AdminEntity adminEntity) {
        return adminMapper.updateById(adminEntity);
    }

    @Override
    public int updateAdmin(Map<String, Object> condition) {
        UpdateWrapper<AdminEntity> updateWrapper = new UpdateWrapper<AdminEntity>();
        updateWrapper.eq("id",MapUtils.getString(condition,"id"));
        // 设置需要修改的字段
        updateWrapper.set("updateTime", new Date());
        // .......


        return adminMapper.update(null,updateWrapper);
    }
}
