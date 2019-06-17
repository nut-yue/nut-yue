package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.ParkspaceBean;
import com.cbd.demo.dao.IParkspaceDao;
import com.cbd.demo.entity.ParkspaceEntity;
import com.cbd.demo.mapper.ParkspaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : ParkspaceDaoImpl
 * @Date ：2019/5/31 10:41
 * @Desc ：类的介绍： cdb车位持久层实现类
 * @author：作者：胡平
 */
@Repository
public class ParkspaceDaoImpl implements IParkspaceDao{

    @Autowired
    private ParkspaceMapper parkspaceMapper;




    /**
     * 查询所有的空闲车位、过滤条件
     * @param parkspaceStatus 状态
     * @param endDate 租赁的时间
     * @param tenantryId 外部合约的id
     * @return
     */
    @Override
    public List<ParkspaceEntity> listLeisureParkspacce(String parkspaceStatus, String endDate,  String tenantryId) {
        return parkspaceMapper.listLeisureParkspacce(parkspaceStatus, endDate, tenantryId);
    }


    @Override
    public ParkspaceEntity selectById(Serializable id) throws Exception {
        return parkspaceMapper.selectById(id);
    }

    @Override
    public int insert(ParkspaceEntity parkspaceEntity) throws Exception {
        return parkspaceMapper.insert(parkspaceEntity);
    }


    @Override
    public int updateById(ParkspaceEntity entity) throws Exception {
        return parkspaceMapper.updateById(entity);
    }

    @Override
    public int updateWrapper(ParkspaceEntity entity, Wrapper<ParkspaceEntity> wrapper) throws Exception{
        return parkspaceMapper.update(entity, wrapper);
    }


    @Override
    public IPage<ParkspaceEntity> selectPage(IPage<ParkspaceEntity> page,
                                           Wrapper<ParkspaceEntity> queryWrapper) throws Exception {
        return parkspaceMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int updateWarpper(ParkspaceEntity entity, Wrapper<ParkspaceEntity> wrapper) throws Exception {
        return parkspaceMapper.update(entity,wrapper);
    }

    @Override
    public List<ParkspaceEntity> selectList(Wrapper<ParkspaceEntity> queryWrapper) throws Exception {
        return parkspaceMapper.selectList(queryWrapper);
    }

    @Override
    public int deleteList(List<Integer> list) {
        return parkspaceMapper.deleteBatchIds(list);
    }
}
