package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.ParkspaceBean;
import com.cbd.demo.entity.ParkspaceEntity;

import java.util.List;

/**
 * @ClassName : IParkspaceService
 * @Date ：2019/5/28 15:37
 * @Desc ：类的介绍：cdb车位业务接口
 * @author：胡平
 * ParkspaceBean 转 ParkspaceEntity
 * ParkspaceEntity中的TenantryBean的tenantryId，赋值给ParkspaceEntity的tenantryId
 * ParkspaceEntity中的ExternalBean的externalId，赋值给ParkspaceEntity的externalId
 * ParkspaceEntity 转 ParkspaceBean （同理）
 *
 * 新增一个方法，按车位ID查看车位详情
 */
public interface IParkspaceService {

    /**（按车位ID查看车位详情）
     * @param parkspaceId 车位ID
     * @return 车位Bean
     */
    ParkspaceBean getParkspace(int parkspaceId);

    /**（批量添加车位）
     * parkspaceStatus 字段默认是：空闲
     * @param ParkspaceBeans cdb车位的ParkspaceBean的集合，
     * @return int类型 成功影响函数、失败0
     */
    int saveParkspace(List<ParkspaceBean> ParkspaceBeans);

    /**（根据parkspaceId修改）
     * 修改cdb车位的消息，主要修改字段parkspaceStatus 【空闲、已租赁】
     * @param parkspaceIds  cdb车位的parkspaceId的集合
     * @param parkspaceStatus 状态，空闲还是已租赁
     * @return int类型 成功影响函数、失败0
     */
    int updateParkspace(List<Integer> parkspaceIds, String parkspaceStatus);

    /** （查询cdb的所有车位，带分页）
     * @param currentPage 当前页数
     * @param pageSize 每页显示条数
     * @param parkspaceStatus 状态精确查询
     * @param parkspaceAddress 地址模糊查询
     * @return IPage 分页对象
     */
    IPage<ParkspaceEntity> listParkspace(int currentPage, int pageSize, String parkspaceStatus,
                                        String parkspaceAddress);

    /**
     * 查询所有的空闲车位、过滤条件
     * @param parkspaceStatus 状态
     * @param endDate 租赁的时间
     * @param tenantryId 外部合约的id
     * @return
     */
    List<ParkspaceEntity> listLeisureParkspacce(String parkspaceStatus, String endDate,  String tenantryId);

    /** （修改cdb车位的状态）
     * @param tenantryId 租户合约的id
     * @param parkspaceStatus  修改的状态【空闲、已租赁】
     * @return int类型 成功影响行数、失败0
     */
    int updateByTenantryId(int tenantryId, String parkspaceStatus);


    /** (租户合约解约后，根据tenantryId修改）
     *  根据租户合约的id修改车位的 parkspaceStatus【空闲】、tenantryId字段设为null
     * @param tenantryId
     * @return
     */
    int updateByTenantryId(int tenantryId);

    /**
     * 根据合同ID查询企业的所有合同
     * @param tenantryId 合同ID
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return  IPage<ParkspaceBean> 分页对象
     */
    IPage<ParkspaceEntity> listTenantryId(int tenantryId, int currentPage, int pageSize);

}
