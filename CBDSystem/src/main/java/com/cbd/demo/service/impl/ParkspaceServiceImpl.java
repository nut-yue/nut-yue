package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.ParkspaceBean;
import com.cbd.demo.bean.TenantryBean;
import com.cbd.demo.dao.IParkspaceDao;
import com.cbd.demo.dao.ITenantryDao;
import com.cbd.demo.entity.ParkspaceEntity;
import com.cbd.demo.entity.TenantryEntity;
import com.cbd.demo.service.IParkspaceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName : ParkspaceServiceImpl
 * @Date ：2019/6/1 16:16
 * @Desc ：类的介绍：cdb车位业务实现类
 * @author：作者：胡平 更改过分页 要上传
 */
@Service
@Transactional
public class ParkspaceServiceImpl implements IParkspaceService {


    @Autowired
    private IParkspaceDao parkspaceDao;

    @Autowired
    private ITenantryDao tenantryDao;

    private final String PARKSPACE_STATUS = "空闲";


    /**
     * 查询所有的空闲车位、过滤条件
     * @param parkspaceStatus 状态
     * @param endDate 租赁的时间
     * @param tenantryId 外部合约的id
     * @return
     */
    @Override
    public List<ParkspaceEntity> listLeisureParkspacce(String parkspaceStatus, String endDate, String tenantryId) {
        try {
            List<ParkspaceEntity> list = parkspaceDao.listLeisureParkspacce(parkspaceStatus, endDate, tenantryId);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * （按车位ID查看车位详情）
     * @param parkspaceId 车位ID
     * @return 车位Bean
     */
    @Override
    public ParkspaceBean getParkspace(int parkspaceId) {
        ParkspaceEntity parkspaceEntity= new ParkspaceEntity();
        TenantryBean tenantryBean = new TenantryBean();
        try {
            parkspaceEntity = parkspaceDao.selectById(parkspaceId);
            TenantryEntity tenantryEntity = tenantryDao.findById(parkspaceEntity.getTenantryId());

            BeanUtils.copyProperties(tenantryEntity,tenantryBean);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ParkspaceBean parkspaceBean = new ParkspaceBean();
        BeanUtils.copyProperties(parkspaceEntity,parkspaceBean);
        parkspaceBean.setTenantryBean(tenantryBean);
        return parkspaceBean;
    }

    /**
     * （批量添加车位）
     * parkspaceStatus 字段默认是：空闲
     *
     * @param ParkspaceBeans cdb车位的ParkspaceBean的集合，
     * @return int类型 成功影响函数、失败0
     */
    @Override
    public int saveParkspace(List<ParkspaceBean> ParkspaceBeans) {
        try {
            ParkspaceEntity entity = null;
            int i = 0;
            for (ParkspaceBean bean : ParkspaceBeans) {
                entity = new ParkspaceEntity();
                BeanUtils.copyProperties(bean, entity);
                entity.setParkspaceStatus(PARKSPACE_STATUS);
//                entity.setExternalId(bean.getExternalBean().getExternalId());
                i += parkspaceDao.insert(entity);
            }
            return i;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**（根据parkspaceId修改）
     * 修改cdb车位的消息，主要修改字段parkspaceStatus 【空闲、已租赁】
     * @param parkspaceIds  cdb车位的parkspaceId的集合
     * @param parkspaceStatus 状态，空闲还是已租赁
     * @return int类型 成功影响函数、失败0
     */
    @Override
    public int updateParkspace(List<Integer> parkspaceIds, String parkspaceStatus) {
        try {
            ParkspaceEntity entity = null;
            int i = 0;
            for (Integer id : parkspaceIds) {
                entity = new ParkspaceEntity();
                entity.setParkspaceId(id);
                entity.setParkspaceStatus(parkspaceStatus);
                i += parkspaceDao.updateById(entity);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** （查询cdb的所有车位，带分页）
     * @param currentPage 当前页数
     * @param pageSize 每页显示条数
     * @param parkspaceStatus 状态精确查询
     * @param parkspaceAddress 地址模糊查询
     * @return IPage 分页对象
     */
    @Override
    public IPage<ParkspaceEntity> listParkspace(int currentPage, int pageSize,
                                                String parkspaceStatus, String parkspaceAddress) {
        // 1 健壮性判断，并且给一些值付默认值
        if (currentPage < 0) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        // 2 创建条件查询
        QueryWrapper<ParkspaceEntity> wrapper = new QueryWrapper<ParkspaceEntity>();
        if (!StringUtils.isEmpty(parkspaceStatus)) {
            wrapper.eq("parkspaceStatus", parkspaceStatus);
        }
        if (!StringUtils.isEmpty(parkspaceAddress)) {
            wrapper.like("parkspaceAddress", parkspaceAddress);
        }
        // 3 创建分页对象，添加分页的条件
        IPage<ParkspaceEntity> iPage = new Page<ParkspaceEntity>(currentPage, pageSize);


        try {
            return parkspaceDao.selectPage(iPage, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** （修改cdb车位的状态）
     * @param tenantryId 租户合约的id
     * @param parkspaceStatus  修改的状态【空闲、已租赁】
     * @return int类型 成功影响行数、失败0
     */
    @Override
    public int updateByTenantryId(int tenantryId, String parkspaceStatus) {
        // 1 健壮性判断
        if (!("空闲".equals(parkspaceStatus) || "已租赁".equals(parkspaceStatus))) {
            return 0;
        }
        // 2 创建条件对象
        UpdateWrapper<ParkspaceEntity> wrapper = new UpdateWrapper<ParkspaceEntity>();
        wrapper.eq("tenantryId", tenantryId).set("parkspaceStatus", parkspaceStatus);

        try {
            return parkspaceDao.updateWrapper(null, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** (租合约解约后，根据externalId修改）
     *  根据外部合约的id修改车位的 parkspaceStatus【空闲】、tenantryId字段设为0
     * @param tenantryId
     * @return
     */
    @Override
    public int updateByTenantryId(int tenantryId) {
        // 1 创建条件对象
        UpdateWrapper<ParkspaceEntity> wrapper = new UpdateWrapper<ParkspaceEntity>();
        wrapper.eq("tenantryId", tenantryId).set("parkspaceStatus", PARKSPACE_STATUS).
                set("tenantryId", 0);
        try {
            return parkspaceDao.updateWrapper(null, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 根据合同ID查询企业的所有车辆
     * @param tenantryId 合同ID
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return  IPage<ParkspaceBean> 分页对象
     */
    @Override
    public IPage<ParkspaceEntity> listTenantryId(int tenantryId, int currentPage, int pageSize) {
        // 1 健壮性判断，并且给一些值付默认值
        if (currentPage < 0) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }

        // 2 动态条件
        QueryWrapper<ParkspaceEntity> wrapper = new QueryWrapper<ParkspaceEntity>();
        wrapper.eq("tenantryId", tenantryId);

        // 3 分页参数
        IPage<ParkspaceEntity> page = new Page<ParkspaceEntity>();
        page.setPages(currentPage);
        page.setSize(pageSize);
        try {
            return parkspaceDao.selectPage(page, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
