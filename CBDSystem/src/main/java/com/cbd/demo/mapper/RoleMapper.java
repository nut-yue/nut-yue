package com.cbd.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.PowerEntity;
import com.cbd.demo.entity.RoleEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @ClassName : IRoleService
 * @Date ：2019/5/28 15:28
 * @Desc ：类的介绍：角色持久化接口
 * @author：作者：王佳伟
 */
public interface RoleMapper extends SqlMapper, BaseMapper<RoleEntity> {
    /**
     * 查询全部角色对象
     * @return 角色对象的list集合
     */
    @Select("select * from t_role where 1=1")
     List<RoleEntity> listRoleEntity();

    @Insert("insert into t_role values (null,#{roleName},#{name})")
    @Options(useGeneratedKeys=true, keyProperty="roleId", keyColumn="id")
    int addRole(RoleEntity roleEntity);
    /**
     * 根据用户id查询角色信息，同时查询权限信息
     * @param adminId 用户id
     * @return 角色信息对象
     */
    @Select("select  * from t_role where roleId = #{adminId}")
    @Results(
            @Result(property = "power",column = "roleId",
            many = @Many(select = "com.cbd.demo.mapper.RoleMapper.listPower",fetchType = FetchType.LAZY))
    )
    RoleEntity getRole(int adminId);
    /**
     * 根据角色id，查找该角色的全部权限。
     * @param roleId
     * @return
     */
    @Select("select * from t_power where powerId in (select powerId from t_roleandpower where roleId=#{roleId})")
    List<PowerEntity> listPower(int roleId);

    @Insert("insert into t_roleandpower values(null,#{roleId},#{powerId})")
    int addRoleAndPowerId(@Param("roleId") int roleId,@Param("powerId") int powerId);

    @Select("select roleId,roleName from t_role where roleName=#{name} ")
    RoleEntity getRoleName(String name);

    @Select("select count(roleName) from t_role where roleName=#{name} ")
    int getRoleCount(String name);

    /**
     * 根据角色id删除中间表的内容
     * @param id 角色id
     * @return
     */
    @Delete("delete from t_roleandpower where roleId=#{id}")
    int delPower(int id);
}
