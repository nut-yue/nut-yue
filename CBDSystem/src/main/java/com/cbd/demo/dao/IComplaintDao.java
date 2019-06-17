package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.ComplaintBean;
import com.cbd.demo.entity.ComplaintEntity;

import java.io.Serializable;

/**
 * @InterfaceName : IComplaintDao
 * @Date ：2019/5/30 22:24
 * @Desc ：接口的介绍： 投诉持久接口
 * @author：陈云强
 * @change：峗权（增加了根据账单ID查询投诉对象）
 */
public interface IComplaintDao {
    /**
     * 分页查询
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return 分页封装对象
     */
    IPage<ComplaintEntity> selectPage(IPage<ComplaintEntity> page, Wrapper<ComplaintEntity> queryWrapper) throws Exception;

    /**
     * 查看单个投诉记录，同时根据投诉双方id查看双方的姓名
     * @param id 投诉记录id
     * @return  ComplaintEntity类型的投诉实体对象
     */
    ComplaintEntity getComplaint(Serializable id) throws Exception;
    /**
     * 添加投诉，将投诉状态设置为“审核中”，再添加
     * @param complaint 投诉对象
     * @return 是否成功，失败返回0，成功返回1
     */
    int insertComplaint(ComplaintEntity complaint) throws Exception;
    /**
     * 管理员修改投诉信息
     * @param complaint 投诉信息对象
     * @return 是否成功，失败返回0，成功返回1
     */
    int updateComplaint(ComplaintEntity complaint,Wrapper<ComplaintEntity> queryWrapper) throws Exception;

    /**
     * 条件查询单个投诉对象
     * @param wrapper 账单ID
     * @return 投诉对象
     * @throws Exception
     */
    ComplaintEntity getComplaintEntity(QueryWrapper<ComplaintEntity> wrapper)throws Exception;
}