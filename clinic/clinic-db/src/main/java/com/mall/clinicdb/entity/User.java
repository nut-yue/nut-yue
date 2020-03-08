package com.mall.clinicdb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Set;

/**
 * @ClassName : User
 * @Date ：2020/2/10 17:12
 * @author：nut-yue
 */
@Data
@TableName("clinic_user")
public class User {
    private String userId;
    private String userName;
    private String password;
    private Set<Role> roles;
}
