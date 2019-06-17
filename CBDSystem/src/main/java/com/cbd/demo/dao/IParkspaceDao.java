package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.ParkspaceEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : IParkspaceService
 * @Date ：2019年5月31日11:12:22
 * @Desc ：类的介绍：cdb车位持久化接口
 * @author：作者：胡平
 */
public interface IParkspaceDao {

    /**
     * 查询所有的空闲车位、过滤条件
     * @param parkspaceStatus 状态
     * @param endDate 租赁的时间
     * @param tenantryId 外部合约的id
     * @return
     */
    List<ParkspaceEntity> listLeisureParkspacce(String parkspaceStatus, String endDate,  String tenantryId);

    /**
     * 按id查询
     * @param id 主键id
     * @return 对象实例
     */
    ParkspaceEntity selectById(Serializable id) throws Exception;

    /**
     * 添加记录
     * @param parkspaceEntity 短消息的实体类
     * @return  操作记录条数
     */
    int insert(ParkspaceEntity parkspaceEntity) throws Exception;

    /**
     * 通过id修改
     * @param entity 对象实例
     * @return
     */
    int updateById(ParkspaceEntity entity) throws Exception;

    /**
     * 条件修改
     * @param wrapper
     * @return
     */
    int updateWrapper(ParkspaceEntity entity, Wrapper<ParkspaceEntity> wrapper) throws Exception;

    /**
     * 按条件分页查询
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return 分页封装对象
     */
    IPage<ParkspaceEntity> selectPage(IPage<ParkspaceEntity> page,
                                      Wrapper<ParkspaceEntity> queryWrapper) throws Exception;

    /**
     * 条件修改
     * @param wrapper
     */
    int updateWarpper(ParkspaceEntity entity, Wrapper<ParkspaceEntity> wrapper) throws Exception;


    List<ParkspaceEntity> selectList(Wrapper<ParkspaceEntity> queryWrapper) throws Exception;

    /**
     * 批量删除
     * @return
     */
    int deleteList(List<Integer> list);
}
