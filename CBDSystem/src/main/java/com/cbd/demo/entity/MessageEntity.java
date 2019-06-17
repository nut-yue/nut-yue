package com.cbd.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : MessageEntity
 * @Date ：2019/5/28 14:15
 * @Desc ：类的介绍：短消息实体类
 * @author：岳超
 */
@Data
@TableName("t_message")
public class MessageEntity {
    /**短消息id*/
    @TableId(value = "messageId",type = IdType.AUTO)
    private Integer messageId;
    /**标题*/
    private String messageTitle;
    /**内容*/
    private String messageContent;
    /**时间*/
    private String messageTime;
    /**类型（系统消息、买买车消息、租车消息）*/
    private String messageType;
    /**是否已读 是否已读 0是未读，1是已读*/
    private int messageIsRead;
    /**发送者id*/
    private Integer messagePostUserId;
    /**接收者id*/
    private Integer messageGetUserId;
}
