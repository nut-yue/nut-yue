package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.cbd.demo.bean.MessageBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.entity.MessageEntity;
import com.cbd.demo.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : MessageServiceImplTest
 * @Date ：2019/5/31 17:43
 * @Desc ：类的介绍： 短消息的测试类
 * @author：作者：胡平
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceImplTest {

    @Autowired
    private IMessageService messageService;


    @Test
    public void add() {
        MessageBean messageBean = new MessageBean();
        messageBean.setMessageContent("欢迎来到德莱联盟");
        messageBean.setMessageTitle("购车消息");
        messageBean.setMessageTime(DateUtils.getDateTime());
        messageBean.setMessageType("购车消息");
        UserBean user = new UserBean();
        user.setUserId(1);
        messageBean.setMessagePostUserBean(user);
        UserBean user1 = new UserBean();
        user1.setUserId(2);
        messageBean.setMessageGetUserBean(user1);
        int i = messageService.saveMessage(messageBean);
        Assert.eq(i, 1, null);
    }

    //发送
    @Test
    public void listSend() {
        IPage<MessageEntity> messages =
                messageService.listMySendMessage(1, 10, 1, 1);
        Assert.notNull(null, messages);
    }
    //接受的消息
    @Test
    public void listMy() {
        IPage<MessageEntity> messages =
                messageService.listMyTakeMessage(1, 10, 8, 1);
        Assert.notNull(null, messages);
    }

    //批量删除
    @Test
    public void deletes() {
        List list = new ArrayList();
        list.add(15);
        list.add(16);
        int i = messageService.deleteMessages(list);
    }

    //批量修改
    @Test
    public void updates() {
        List list = new ArrayList();
        list.add(15);
        list.add(16);
        list.add(17);
        int i = messageService.updateMessages(list, 1);
        System.out.println(i +"=================");
    }
}
