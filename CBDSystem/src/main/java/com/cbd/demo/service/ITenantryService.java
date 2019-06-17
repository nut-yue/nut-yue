package com.cbd.demo.service;/**
 * Created by 123 on 2019/5/28.
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.TenantryBean;
import com.cbd.demo.entity.TenantryEntity;

import java.sql.Wrapper;
import java.util.List;

/**
 * @ClassName : ITenantryService
 * @Date ：2019/5/28 15:29
 * @Desc ：接口的介绍：租户合约业务逻辑接口
 * @author：作者： 刘划轩
 */
public interface ITenantryService {
    /**
     * 新增租户合同，同时将租户合同中的车位状态改为已租赁状态，
     * 生成一条CBD计费信息,企业计费信息
     * @param tenantryBean 租户合同对象
     * @param ids cbd车位id集合
     */
    int addNewTenantry(TenantryBean tenantryBean, List<Integer> ids);
    /**
     * 租户合同续约，添加新的续约合同，同时将租户合同中的车位状态改为已租赁状态，
     * 生成一条CBD计费信息,企业计费信息
     * @param tenantryBean 租户合同对象
     */
    int contractTenantry(TenantryBean tenantryBean,List<Integer> ids);

    /**
     * 租户合同解约，修改合同状态为解约，相关车位状态改为空闲，
     * 企业和CBD计费系统做相应的更新（解约分为时间到自动节约，提前解约。统一用：
     * 合约总金额=合约总金额-（总金额/合约使用总月数）*（总月份-已使用月份）
     * @param tenantryId 租户合同Id
     */
    int updateTenantry(int tenantryId);

    /**
     * 动态条件查询租户合同，显示分页
     * @param currentPage 起始页面
     * @param pageSize 页面显示条数
     * @param starTime 合同起始时间
     * @param endTime 合同结束时间
     * @param tenantryUserName 企业名称
     * @return
     */
    IPage<TenantryEntity> showAllTenantry(int currentPage, int pageSize, String starTime, String endTime, String tenantryUserName);

    /**
     * 通过租户合同ID查询租户合同详细信息
     * @param id 租户合同ID
     * @return 租户合同实体对象
     */
    TenantryBean findById(int id);

}
