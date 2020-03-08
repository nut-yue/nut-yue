package com.mall.clinicdb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : Permission
 * @Date ：2020/2/10 17:12
 * @author：nut-yue
 */
@Data
@TableName("clinic_permission")
public class Permission {
    private String permissionId;
    private String permission;
}
