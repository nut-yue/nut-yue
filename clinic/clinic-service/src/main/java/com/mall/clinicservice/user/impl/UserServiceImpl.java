package com.mall.clinicservice.user.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.clinicdb.entity.*;
import com.mall.clinicdb.mapper.UserMapper;
import com.mall.clinicservice.user.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.Map;
import java.util.Set;

/**
 * @ClassName : UserServiceImpl
 * @Date ：2019/12/24 11:10
 * @author：nut-yue
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insertUser(UserEntity userEntity) {
        return userMapper.insert(userEntity);
    }

    @Override
    public int deleteUser(int UserId) {
        return userMapper.deleteById(UserId);
    }

    @Override
    public int updateUser(UserEntity userEntity) {
        UpdateWrapper <UserEntity> wrapper = new UpdateWrapper<UserEntity>();
        wrapper.eq("id",userEntity.getUserId());
        return userMapper.update(userEntity,wrapper);
    }

    @Override
    public IPage<UserEntity> listUsers (Map<String,Object> condition) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<UserEntity>();
        int currentPage = MapUtil.getInt(condition,"currentPage");
        int pageSize = MapUtil.getInt(condition,"pageSize");
        Page<UserEntity> page = new Page<UserEntity>(currentPage,pageSize);
        IPage<UserEntity> pages = userMapper.selectPage(page, wrapper);
        return pages;
    }

    @Override
    public UserEntity getUser(int UserId) {
        return userMapper.selectById(UserId);
    }

    @Override
    public UserEntity login(Map<String, Object> condition) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<UserEntity>();
        wrapper.allEq(condition);
        UserEntity userEntity = userMapper.selectOne(wrapper);
        return userEntity;
    }

    @Override
    public User getUserInfo(String username) {
        // 创建查询器，根据用户名查询出用户信息
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<UserEntity>();
        wrapper.eq("username",username);
        // 创建user对象，用于封装用户的角色及权限，将查询的用户对象信息封装到user中
        User user = new User();
        UserEntity userEntity = userMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(userEntity)) {
            BeanUtils.copyProperties(userEntity,user);
            // 根据用户id查询角色集合
            String userId = userEntity.getUserId();
            Set<Role> roleList = userMapper.getRoles(userId);
            for (Role role : roleList) {
                // 根据角色id将权限封装到角色中
                Set <Permission> permissions = userMapper.getPermissions(role.getRoleId());
                role.setPermissions(permissions);
            }
            // 将角色封装到用户中
            user.setRoles(roleList);
        }
        return user;
    }

}
