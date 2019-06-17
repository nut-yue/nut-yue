package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cbd.demo.dao.IUserDao;
import com.cbd.demo.entity.UserEntity;
import com.cbd.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @ClassName : UserDaoImpl
 * @Date ：2019/5/31 10:49
 * @Desc ：类的介绍：个人用户持久层实现
 * @author：作者：陈玉婷
 */
@Repository
public class UserDaoImpl implements IUserDao {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据ID查询用户对象
     * @param id 用户ID
     * @return 用户实体类
     */
    @Override
    public UserEntity getById(Serializable id) throws Exception {
        return userMapper.selectById(id);
    }
    /**
     * 根据ID修改对象
     * @param user 用户实体类
     * @return 修改的条数
     */
    @Override
    public int updateById(UserEntity user) throws Exception {
        return userMapper.updateById(user);
    }

    /**
     * 添加用户对象
     * @param user 用户实体类
     * @return 添加的条数
     */
    @Override
    public int addUser(UserEntity user) throws Exception {
        return userMapper.insert(user);
    }

    @Override
    public UserEntity getByUserId(Wrapper<UserEntity> wrapper) throws Exception {
        return userMapper.selectOne(wrapper);
    }
}
