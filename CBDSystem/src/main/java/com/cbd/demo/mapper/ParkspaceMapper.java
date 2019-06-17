package com.cbd.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.ParkspaceEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName : IParkspaceService
 * @Date ：2019/5/28 15:37
 * @Desc ：类的介绍：cdb车位Mapper接口
 * @author：作者：王佳伟
 */
public interface ParkspaceMapper extends SqlMapper, BaseMapper<ParkspaceEntity> {

    @Select("select park.parkspaceId, park.parkspaceAddress, park.parkspaceRegionNumber, park.parkspaceNumber from " +
            "t_parkspace as park left join  t_external as ex on park.externalId = ex.externalId " +
            "where ((park.parkspaceStatus=#{par} or park.tenantryId= #{tenantryId}) and ex.externalDeadline > #{date})")
    List<ParkspaceEntity> listLeisureParkspacce(@Param("par") String parkspaceStatus, @Param("date") String endDate,
                                                @Param("tenantryId") String tenantryId);
}
