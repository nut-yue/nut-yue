package com.mall.clinicmall_db.mapper;


import com.mall.clinicmall_db.entity.CollectEntity;

public interface ClinicMallCollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CollectEntity record);

    int insertSelective(CollectEntity record);

    CollectEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectEntity record);

    int updateByPrimaryKey(CollectEntity record);
}
