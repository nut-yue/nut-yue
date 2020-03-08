package com.mall.clinicdb.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName : UserBean
 * @Date ：2020/2/9 20:07
 * @author：nut-yue
 */
@Data
public class UserBean {
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**角色集合*/
    private List<String> roles;
    /**权限集合*/
    private List<String> permissions;
}
