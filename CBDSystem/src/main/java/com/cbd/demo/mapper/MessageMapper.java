package com.cbd.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.MessageEntity;


/**
 * @InterfaceName : MessageMapper
 * @Date ：2019/5/28 14:24
 * @Desc ：接口的介绍： 短消息mapper接口
 * @author：岳超
 */
public interface MessageMapper extends SqlMapper, BaseMapper<MessageEntity> {
}
