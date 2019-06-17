package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : MessageBean
 * @Date ：2019/5/28 14:15
 * @Desc ：类的介绍：短消息实体Bean
 * @author：岳超
 */
@Data
public class MessageBean {
    /**短消息id*/
    private Integer messageId;
    /**标题*/
    private String messageTitle;
    /**内容*/
    private String messageContent;
    /**时间*/
    private String messageTime;
    /**类型（系统消息、买买车消息、租车消息）*/
    private String messageType;
    /**是否已读 0是未读，1是已读*/
    private int messageIsRead;
    /**发送者*/
    private UserBean messagePostUserBean;
    /**接收者*/
    private UserBean messageGetUserBean;
}
