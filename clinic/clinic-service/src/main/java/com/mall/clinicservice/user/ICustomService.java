package com.mall.clinicservice.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.clinicdb.entity.CustomEntity;


import java.util.List;
import java.util.Map;

/**
 * @ClassName : ICustomService
 * @Date ：2020/2/23 21:06
 * @author：nut-yue
 */
public interface ICustomService {

    /**
     * 添加客户信息(微信授权和手动维护共用)
     * @param customEntity 客户对象
     * @return 添加成功条数
     */
    int insertCustom(CustomEntity customEntity);

    /**
     * 删除客户记录
     * @param ids 客户id集合
     * @return 删除记录成功条数
     */
    int deleteBatchCustom(List<String> ids);

    /**
     * 修改客户信息
     * @param customEntity 客户对象
     * @return 修改成功条数
     */
    int updateCustom(CustomEntity customEntity);

    /**
     * 分页查询客户集合
     * @param page 分页对象
     * @param condition 查询条件
     * @return 封装查询结果的分页对象
     */
    IPage<CustomEntity> listCustoms(Page<CustomEntity> page,Map <String, Object> condition);

    /**
     * 根据客户id查询客户信息
     * @param customId 客户id
     * @return 客户信息
     */
    CustomEntity getCustom(String customId);
}
