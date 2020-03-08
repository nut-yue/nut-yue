package com.mall.clinicdb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Set;

/**
 * @ClassName : Role
 * @Date ：2020/2/10 17:12
 * @author：nut-yue
 */
@Data
@TableName("clinic_role")
public class Role {
    private String roleId;
    private String roleName;
    private Set<Permission> permissions;
}
