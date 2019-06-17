package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.UserBean;

/**
 * @ClassName : IUserService
 * @Date ：2019/5/28 18:04
 * @Desc ：类的介绍：个人用户接口
 * @author：作者：王佳伟
 */
public interface IUserService {

    /**
     * 根据用户编号查询用户详细信息,根据交易次数和投诉次数计算出信誉值。
     * 信誉度=（1-被投诉次数/总交易次数）*5
     * @param id 用户id
     * @return 个人用户对象
     */
    UserBean getUser(int id);
    /**
     * 修改个人用户信息
     * @param user 个人用户对象
     * @return 是否成功，0是失败，1是成功
     */
    int updateUser(UserBean user);

    /**
     * 个人用户注册方法，同时根据个人用户Bean中的adminBean属性添加用户表的数据。并将adminBean中的角色类型设置为1.
     * @param user 个人用户对象
     * @return 成功返回1,失败返回0
     */
    int saveUser(UserBean user);

    /**
     * 个人用户注册方法
     * @param user
     * @return
     */
    int saveUsers(UserBean user);
    /**
     * 根据用户userid获得用户对象
     * @param userId 用户id
     * @return 用户对象
     */
    UserBean getUserId(int userId);
}
