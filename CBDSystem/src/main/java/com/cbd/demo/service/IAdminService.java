package com.cbd.demo.service;

import com.cbd.demo.bean.AdminBean;

/**
 * @ClassName : AdminService
 * @Date ：2019/5/28 15:22
 * @Desc ：类的介绍：用户业务接口
 * @author：作者：王佳伟
 */
public interface IAdminService {
    /**
     * 登录方法，用户名查找用户，shiro使用
     * @param adminName 用户名
     * @return 用户对象
     */
     AdminBean getAdmin(String adminName);
    /**
     * 登录方法，根据用户名，密码，查找用户。,,
     * @param adminName 用户名
     * @param adminPassword 密码
     * @return AdminBean类型的用户对象
     */
     AdminBean getAdmin(String adminName, String adminPassword);

    /**
     * 根据id，需要使用md5对密码加密，，加密方式=密码+用户名进行md5的转换
     * @param id 用户id，用户id
     * @param newPwd 新密码
     * @return 返回是否成功，失败返回0 成功返回1
     */
     int updateAdmin(int id, String newPwd);


}
