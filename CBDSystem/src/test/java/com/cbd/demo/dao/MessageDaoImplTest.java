package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.dao.impl.MessageDaoImpl;
import com.cbd.demo.entity.MessageEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageDaoImplTest {
    @Autowired
    private IMessageDao iMessageDao = new MessageDaoImpl();

    @Test
    public void insertTest(){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessageIsRead(0);
        messageEntity.setMessageTitle("哈哈哈哈");
        int insert = 0;
        try {
            insert = iMessageDao.insert(messageEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals(0,insert);
    }

    @Test
    public void selectPageTest(){
        IPage<MessageEntity> page = new Page<>(1,2);
        QueryWrapper<MessageEntity> queryWrapper =new QueryWrapper<>();
        queryWrapper.like("messageTitle","系统信息");
        IPage<MessageEntity> page1 = null;
        try {
            page1 = iMessageDao.listPage(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(page1);
    }
    @Test
    public void deleteBatchIdsTest(){
        List list = new ArrayList();
        list.add(13);
        int i = 0;
        try {
            i = iMessageDao.deleteBatchIds(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals(0,i);
    }

    @Test
    public void updateByIdTest(){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessageId(1);
        messageEntity.setMessageTitle("hhhhhhhhh");
        int i = 0;
        try {
            i = iMessageDao.updateById(messageEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotEquals(0,i);
    }
}
