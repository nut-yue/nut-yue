package com.cbd.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.AdministratorEntity;
import com.cbd.demo.entity.UserEntity;

/**
 * @ClassName : IUserDao
 * @Date ：2019/5/28 15:44
 * @Desc ：类的介绍：个人用户持久化接口
 * @author：作者：王佳伟
 */
public interface UserMapper extends SqlMapper, BaseMapper<UserEntity> {
}
