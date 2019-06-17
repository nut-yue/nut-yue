package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.MessageEntity;

import java.io.Serializable;
import java.util.Collection;

/**
 * @InterfaceName : IMessageDao
 * @Date ：2019/5/28 14:24
 * @Desc ：接口的介绍： 短消息持久接口
 * @author：胡平
 */
public interface IMessageDao {

    /**
     * 按id查询
     * @param id 主键id
     * @return 对象实例
     */
    MessageEntity selectById(Serializable id) throws Exception;

    /**
     * 添加记录
     * @param messageEntity 短消息的实体类
     * @return  操作记录条数
     */
    int insert(MessageEntity messageEntity) throws Exception;

    /**
     * 按条件分页查询
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return  类型IPage<MessageEntity> 分页封装对象
     */
    IPage<MessageEntity> listPage(IPage<MessageEntity> page, Wrapper<MessageEntity> queryWrapper) throws Exception;

    /**
     * 批量删除
     * @param idList id集合
     * @return 操作条数
     */
    int deleteBatchIds(Collection<? extends Serializable> idList) throws Exception;

    /**
     * 按id修改记录
     * @param entity 对象实例
     * @return 操作记录条数
     */
    int updateById(MessageEntity entity) throws Exception;
}
