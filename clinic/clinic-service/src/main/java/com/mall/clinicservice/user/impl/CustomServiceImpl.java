package com.mall.clinicservice.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.clinicdb.entity.CustomEntity;
import com.mall.clinicdb.mapper.CustomMapper;
import com.mall.clinicservice.user.ICustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : CustomServiceImpl
 * @Date ：2020/2/23 21:07
 * @author：nut-yue
 */
@Service
public class CustomServiceImpl implements ICustomService {

    @Autowired
    private CustomMapper customMapper;


    @Override
    public int insertCustom(CustomEntity customEntity) {
        return customMapper.insert(customEntity);
    }

    @Override
    public int deleteBatchCustom(List<String> ids) {
        return customMapper.deleteBatchIds(ids);
    }

    @Override
    public int updateCustom(CustomEntity customEntity) {
        return customMapper.updateById(customEntity);
    }

    @Override
    public IPage<CustomEntity> listCustoms(Page<CustomEntity> page,Map<String, Object> condition) {
        QueryWrapper<CustomEntity> queryWrapper = new QueryWrapper<CustomEntity>();
        queryWrapper.allEq(condition);
        return customMapper.selectPage(page,queryWrapper);
    }

    @Override
    public CustomEntity getCustom(String customId) {
        return customMapper.selectById(customId);
    }
}
