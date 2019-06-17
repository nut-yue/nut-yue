package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.IMessageDao;
import com.cbd.demo.entity.MessageEntity;
import com.cbd.demo.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ClassName : AdminDaoIpml
 * @Date ：2019/5/31 17:28
 * @Desc ：短消息持久接口实现
 * @author：作者：胡平
 */
@Repository
public class MessageDaoImpl implements IMessageDao {
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 按id查询
     * @param id 主键id
     * @return 对象实例
     */
    @Override
    public MessageEntity selectById(Serializable id) throws Exception {
        return  messageMapper.selectById(id);
    }

    @Override
    public int insert(MessageEntity messageEntity) throws Exception {
        return messageMapper.insert(messageEntity);
    }

    @Override
    public IPage<MessageEntity> listPage(IPage<MessageEntity> page, Wrapper<MessageEntity> queryWrapper) throws Exception  {
        return messageMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> idList) throws Exception  {
        return messageMapper.deleteBatchIds(idList);
    }

    @Override
    public int updateById(MessageEntity entity)  throws Exception {
        return messageMapper.updateById(entity);
    }
}
