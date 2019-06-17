package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.MessageBean;
import com.cbd.demo.entity.MessageEntity;

import java.util.List;

/**
 * @InterfaceName : IMessageService
 * @Date ：2019/5/28 14:24
 * @Desc ：接口的介绍： 短消息业务接口
 * @author：胡平
 *  MessageBean需要转成MessageEntity
 *  MessageBean中messagePostUserBean的userId，需要赋值给MessageEntity的messagePostUserId
 *  MessageBean中messageGetUserBean的userId，需要赋值给MessageEntity的messageGetUserId
 *  MessageEntity需要转成MessageBean(同理）
 *  messageTime的格式 精确到秒钟（例如 xxxx:xx:xx xx:xx:xx)
 */
public interface IMessageService {

    /**
     * 查询单个message
     * @param messageId
     * @return
     */
    MessageBean getByIdMessage(int messageId);

    /**
     *  messageIsRead 默认值 0
     *  messageGetUserId 和 messagePostUserId 必须存在，不存在直接 return 0
     *  messageTime 查看接口注释
     * @param messageBean 消息的实体bean
     * @return int类型 成功返回1， 失败0
     */
    int saveMessage(MessageBean messageBean);

    /**
     * 查询自己的接受的消息
     * @param messageGetUserId 接收者
     * @param messageIsRead 是否阅读、如果需要查询所有的消息、messageIsRead传入-1（注意：不明白问作者
     *                       需要该条件、0未阅读/1已阅读
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return IPage 分页对象
     */
    IPage<MessageEntity> listMyTakeMessage(int currentPage, int pageSize, int messageGetUserId,
                                           int messageIsRead);

    /**
     * 查询自己的发送的短消息
     * @param messagePostUserId 发送者
     * @param messageIsRead 是否阅读、如果需要查询所有的消息、messageIsRead传入-1（注意：不明白问作者
     *                       需要该条件、0未阅读/1已阅读
     * @return IPage 分页对象
     */
    IPage<MessageEntity> listMySendMessage(int currentPage, int pageSize,
                                           int messagePostUserId, int messageIsRead);


    /**
     * 短消息的批量删除
     * @param messageIds 短消息的messageId的集合
     * @return int类型 成功返回影响函数， 失败0
     */
    int deleteMessages(List<Integer> messageIds);


    /**
     * 批量修改个人的messageIsRead字段，修改为messageIsRead的1
     * 只能修改messageIsRead字段，其他字段不修改
     * @param messageIds：  短消息的messageId的集合
     * @param messageIsRead： 是否阅读
     * @return int类型 成功返回影响函数， 失败0
     */
    int updateMessages(List<Integer> messageIds, int messageIsRead);
}
