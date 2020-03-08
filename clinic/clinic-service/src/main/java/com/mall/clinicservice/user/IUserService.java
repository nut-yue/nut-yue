package com.mall.clinicservice.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.clinicdb.entity.User;
import com.mall.clinicdb.entity.UserBean;
import com.mall.clinicdb.entity.UserEntity;

import java.util.Map;

/**
 * @ClassName : IUserService
 * @Date ：2019/12/24 11:09
 * @author：nut-yue
 */
public interface IUserService {

    /**
     * 添加用户
     * @param userEntity 用户实例
     * @return 添加成功条数
     */
    int insertUser(UserEntity userEntity);

    /**
     * 根据id删除用户
     * @param UserId 用户id
     * @return 删除成功条数
     */
    int deleteUser(int UserId);

    /**
     * 修改用户信息
     * @param userEntity 用户实例
     * @return 修改成功条数
     */
    int updateUser(UserEntity userEntity);

    /**
     * 按条件分页查询用户信息
     * @param condition 查询条件
     * @return 分页结果
     */
    IPage<UserEntity> listUsers (Map<String,Object> condition);

    /**
     * 根据用户信息id查询用户信息
     * @param UserId 用户id
     * @return 用户对象
     */
    UserEntity getUser(int UserId);

    /**
     * 登陆
     * @param condition 登陆条件：（username+password）
     * @return 用户对象
     */
    UserEntity login(Map<String,Object> condition);

    /**
     * 获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getUserInfo(String username);
}
