package com.mall.clinicdb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.clinicdb.entity.Permission;
import com.mall.clinicdb.entity.Role;
import com.mall.clinicdb.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @ClassName : UserMapper
 * @Date ：2019/12/27 13:21
 * @author：nut-yue
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 获取用户的角色集合
     * @param userId 用户id
     * @return 角色集合
     */
    Set <Role> getRoles(String userId);

    /**
     * 获取用户的权限集合
     * @param roleId 角色id
     * @return 权限集合
     */
    Set <Permission> getPermissions(String roleId);
}
