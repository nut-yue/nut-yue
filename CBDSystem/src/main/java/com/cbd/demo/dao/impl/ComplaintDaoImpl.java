package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.IComplaintDao;
import com.cbd.demo.entity.ComplaintEntity;
import com.cbd.demo.mapper.ComplaintMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
/**
 * @InterfaceName : ComplaintDaoImpl
 * @Date ：2019/5/31 14:24
 * @Desc ：持久接口实现类
 * @author：陈云强
 * @change:峗权（增加了根据账单ID查看投诉对象的方法实现）
 */
@Repository
public class ComplaintDaoImpl implements IComplaintDao {
    @Autowired
    private ComplaintMapper complaintMapper;
    @Override
    public IPage<ComplaintEntity> selectPage(IPage<ComplaintEntity> page, Wrapper<ComplaintEntity> queryWrapper) throws Exception {
        return complaintMapper.selectPage(page,queryWrapper);
    }

    @Override
    public ComplaintEntity getComplaint(Serializable id) throws Exception {
        return complaintMapper.selectById(id);
    }

    @Override
    public int insertComplaint(ComplaintEntity complaint) throws Exception {
        return complaintMapper.insert(complaint);
    }

    @Override
    public int updateComplaint(ComplaintEntity complaint, Wrapper<ComplaintEntity> queryWrapper) throws Exception {
        return complaintMapper.update(complaint,queryWrapper);
    }

    @Override
    public ComplaintEntity getComplaintEntity(QueryWrapper<ComplaintEntity> wrapper) throws Exception {
        return complaintMapper.selectOne(wrapper);
    }
}
