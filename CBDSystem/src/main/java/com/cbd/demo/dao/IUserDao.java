package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cbd.demo.entity.UserEntity;

import java.io.Serializable;

/**
 * @ClassName : IUserDao
 * @Date ：2019/5/28 15:44
 * @Desc ：类的介绍：个人用户持久化接口
 * @author：作者：峗权
 * @Date:2019/5/31 陈玉婷 添加了抛出异常
 */
public interface IUserDao {
    /**
     * 根据ID查询用户对象
     * @param id 用户ID
     * @return 用户实体类
     */
    UserEntity getById(Serializable id) throws Exception;

    /**
     * 根据ID修改对象
     * @param user 用户实体类
     * @return 修改的条数
     */
    int updateById(UserEntity user) throws Exception;

    /**
     * 添加用户对象
     * @param user 用户实体类
     * @return 添加的条数
     */
    int addUser(UserEntity user)throws Exception;

    /**
     * 根据用户userid获得用户对象消息
     * @param wrapper 用户id
     * @return 用户对象
     * @throws Exception
     */
    UserEntity getByUserId(Wrapper<UserEntity> wrapper)throws Exception;
}
