package com.cbd.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.AdminEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @ClassName : AdminService
 * @Date ：2019/5/28 15:22
 * @Desc ：类的介绍：用户Mapper接口
 * @author：作者：王佳伟
 */
public interface AdminMapper extends SqlMapper, BaseMapper<AdminEntity> {

    @Update("update t_admin set roleId=#{roleId} where adminId=#{adminId}")
    int updateRole(@Param("adminId") int adminId,@Param("roleId") int roleId);
}
