package com.cbd.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.ParkspaceEntity;
import com.cbd.demo.entity.PowerEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName : IPowerService
 * @Date ：2019/5/28 15:26
 * @Desc ：类的介绍：权限Mapper接口
 * @author：作者：王佳伟
 */
public interface PowerMapper extends SqlMapper,BaseMapper<PowerEntity> {
    /**
     * 根据角色id，查找该角色的全部权限。
     * @param roleId
     * @return
     */
    @Select("select * from t_power where powerId in (select powerId from t_roleandpower where roleId=#{roleId})")
    List<PowerEntity> listPower(int roleId);
}
