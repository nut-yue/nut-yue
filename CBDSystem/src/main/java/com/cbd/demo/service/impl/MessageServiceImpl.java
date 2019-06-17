package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.MessageBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.dao.IMessageDao;
import com.cbd.demo.dao.IUserDao;
import com.cbd.demo.entity.MessageEntity;
import com.cbd.demo.entity.UserEntity;
import com.cbd.demo.service.IMessageService;
import com.cbd.demo.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName : MessageServiceImpl
 * @Date ：2019/5/31 16:53
 * @Desc ：类的介绍： 短消息的业务逻辑层的实现
 * @author：作者：胡平
 */
@Service
@Transactional
public class MessageServiceImpl implements IMessageService {

    /**
     * 注入message的dao
     */
    @Autowired
    private IMessageDao messageDao;
    @Autowired
    private IUserDao userDao;

    //短消息是否阅读的默认值 (未阅读）
    private final int MESSAGEISREAD = 0;


    /**
     * 查询单个message
     *
     * @param messageId
     * @return
     */
    @Override
    public MessageBean getByIdMessage(int messageId) {
        MessageBean messageBean = null;
        UserBean userBean=null;
        UserBean userBean1=null;
        try {
            messageBean = new MessageBean();
            userBean=new UserBean();
            userBean1=new UserBean();
            MessageEntity messageEntity = messageDao.selectById(messageId);
            BeanUtils.copyProperties(messageEntity, messageBean);
            UserEntity userEntity=userDao.getById(messageEntity.getMessageGetUserId());
            BeanUtils.copyProperties(userEntity,userBean);

            UserEntity userEntity1=userDao.getById(messageEntity.getMessagePostUserId());
            BeanUtils.copyProperties(userEntity1,userBean1);
            messageBean.setMessageGetUserBean(userBean);
            messageBean.setMessagePostUserBean(userBean1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageBean;
    }

    /**
     * (添加短消息）
     * messageIsRead 默认值 0
     * messageGetUserId 和 messagePostUserId 必须存在，不存在直接 return 0
     * messageTime 查看接口注释
     *
     * @param messageBean 消息的实体bean
     * @return int类型 成功返回1， 失败0
     */
    @Override
    public int saveMessage(MessageBean messageBean) {
        // 1 健壮性判断
        if (StringUtils.isEmpty(messageBean)) {
            return 0;
        }
        if (StringUtils.isEmpty(messageBean.getMessageGetUserBean())) {
            return 0;
        }
        if (StringUtils.isEmpty(messageBean.getMessagePostUserBean())) {
            return 0;
        }

        // 2 实体bean转entity
        MessageEntity messageEntity = new MessageEntity();
        BeanUtils.copyProperties(messageBean, messageEntity);

        // 3 设置entity中特殊字段
        messageEntity.setMessageIsRead(MESSAGEISREAD);
        messageEntity.setMessageTime(DateUtils.getDateTime());
        messageEntity.setMessageGetUserId(messageBean.getMessageGetUserBean().getUserId());
        messageEntity.setMessagePostUserId(messageBean.getMessagePostUserBean().getUserId());

        try {
            return messageDao.insert(messageEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询自己的接受的消息
     *
     * @param messageGetUserId 接收者
     * @param messageIsRead    是否阅读、如果需要查询所有的消息、messageIsRead传入-1（注意：不明白问作者
     *                         需要该条件、0未阅读/1已阅读
     * @param currentPage      当前页
     * @param pageSize         每页显示条数
     * @return IPage 分页对象
     */
    @Override
    public IPage<MessageEntity> listMyTakeMessage(int currentPage, int pageSize,
                                                  int messageGetUserId, int messageIsRead) {

        QueryWrapper wrapper = new QueryWrapper();

        // 1 健壮性判断，并且给一些值付默认值
        if (currentPage < 0) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }


        // 2 动态拼接条件
        wrapper.eq("messageGetUserId", messageGetUserId);
        if (messageIsRead != -1) {
            //根据阅读查询
            wrapper.eq("messageIsRead", messageIsRead);
        }

        //3 分页参数
        IPage<MessageEntity> page = new Page<MessageEntity>();
        page.setPages(currentPage - 1);
        page.setSize(pageSize);

        try {
            return messageDao.listPage(page, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询自己的发送的短消息
     *
     * @param messagePostUserId 发送者
     * @param messageIsRead     是否阅读、如果需要查询所有的消息、messageIsRead传入-1（注意：不明白问作者
     *                          需要该条件、0未阅读/1已阅读
     * @return IPage 分页对象
     */
    @Override
    public IPage<MessageEntity> listMySendMessage(int currentPage, int pageSize,
                                                  int messagePostUserId, int messageIsRead) {

        QueryWrapper wrapper = new QueryWrapper();

        // 1 健壮性判断，并且给一些值付默认值
        if (currentPage < 0) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }

        // 2 动态拼接条件
        wrapper.eq("messagePostUserId", messagePostUserId);
        if (messageIsRead != -1) {
            //根据阅读查询
            wrapper.eq("messageIsRead", messageIsRead);
        }

        //3 分页参数
        IPage<MessageEntity> page = new Page<MessageEntity>();
        page.setPages(currentPage - 1);
        page.setSize(pageSize);

        try {
            return messageDao.listPage(page, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量删除
     *
     * @param messageIds 短消息的messageId的集合
     * @return
     */
    @Override
    public int deleteMessages(List<Integer> messageIds) {
        try {
            return messageDao.deleteBatchIds(messageIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 批量修改状态
     *
     * @param messageIds： 短消息的messageId的集合
     * @return
     */
    @Override
    public int updateMessages(List<Integer> messageIds, int messageIsRead) {
        int i = 0;
        try {
            for (Integer id : messageIds) {
                MessageEntity message = new MessageEntity();
                message.setMessageId(id);
                message.setMessageIsRead(messageIsRead);
                messageDao.updateById(message);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
