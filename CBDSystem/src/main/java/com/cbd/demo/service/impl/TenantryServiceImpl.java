package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.CompanyBean;
import com.cbd.demo.bean.TenantryBean;
import com.cbd.demo.dao.ICompanyBillDao;
import com.cbd.demo.dao.ICompanyDao;
import com.cbd.demo.dao.IParkspaceDao;
import com.cbd.demo.dao.ITenantryDao;
import com.cbd.demo.entity.CompanyBillEntity;
import com.cbd.demo.entity.CompanyEntity;
import com.cbd.demo.entity.ParkspaceEntity;
import com.cbd.demo.entity.TenantryEntity;
import com.cbd.demo.service.ITenantryService;
import com.cbd.demo.util.DateUtils;
import com.cbd.demo.util.MoneyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : TenantryServiceImpl
 * @Date ：2019/6/1 17:05
 * @Desc ：类的介绍：
 * @author：岳超
 */
@Service
public class TenantryServiceImpl implements ITenantryService {

    @Autowired
    private ITenantryDao tenantryDao;
    @Autowired
    private IParkspaceDao parkspaceDao;
    @Autowired
    private ICompanyBillDao companyBillDao;
    @Autowired
    private ICompanyDao companyDao;

    @Override
    public int addNewTenantry(TenantryBean tenantryBean,List<Integer> ids) {
        return addTenantry(tenantryBean,ids);
    }

    @Override
    public int contractTenantry(TenantryBean tenantryBean,List<Integer> ids) {
        return addTenantry(tenantryBean,ids);
    }

    @Override
    public int updateTenantry(int tenantryId) {
        int result = 0;
        try {
            // 创建条件构造器
            UpdateWrapper<TenantryEntity> tenantryWrapper = new UpdateWrapper<>();
            // 根据租户合约id修改合同状态为失效
            tenantryWrapper.eq("tenantryId",tenantryId)
                    .set("tenantryContractStatus","失效");
            result = tenantryDao.updateTenantry(null,tenantryWrapper);
            // 根据合同id修改相关车位状态为空闲
            UpdateWrapper<ParkspaceEntity> parkspaceWrapper = new UpdateWrapper<>();
            parkspaceWrapper.eq("tenantryId",tenantryId)
                            .set("parkspaceStatus","空闲");
            parkspaceDao.updateWarpper(null,parkspaceWrapper);
            // 获得当前合同对象
            TenantryEntity tenantryEntity = tenantryDao.findById(tenantryId);
            // 判断合约是否已过期,-1表示当前时间小于结束时间，即合同提前节约
            if(DateUtils.getDate().compareTo(tenantryEntity.getTenantryEndTime())==-1){
                // 得到实际消费金额
                int realMoney = MoneyUtil.moneyCount(tenantryEntity.getTenantryStartTime(),tenantryEntity.getTenantryEndTime(),tenantryEntity.getTenantryDealPrice());
                // 根据合同id,修改企业计费账单交易金额
                // 创建条件构造器
                UpdateWrapper <CompanyBillEntity> CompanyBillWrapper =new UpdateWrapper<>();
                CompanyBillWrapper.eq("pactId",tenantryId).set("companyBillMoney",realMoney);
                companyBillDao.updateByCondition(null,CompanyBillWrapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public IPage<TenantryEntity> showAllTenantry(int currentPage, int pageSize, String starTime, String endTime, String tenantryUserName) {
        IPage<TenantryEntity> pages = null;
        try {
            // 创建分页对象
            Page<TenantryEntity> page = new Page<>(currentPage,pageSize);
            // 新建条件构造器
            QueryWrapper <TenantryEntity> wrapper = new QueryWrapper<>();
            // 查询时间大于起始日期小于结束日期,按企业名称模糊查询
            wrapper.lt(StringUtils.isNotEmpty(starTime),"tenantryStartTime",starTime)
                    .gt(StringUtils.isNotEmpty(endTime),"tenantryEndTime",endTime)
                    .like(StringUtils.isNotEmpty(tenantryUserName),"tenantryUserName",tenantryUserName);
            pages = tenantryDao.showAllTenantry(page, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pages;
    }

    @Override
    public TenantryBean findById(int id) {
        TenantryBean tenantryBean = new TenantryBean();
        try {
            // 获得租户合约实体类对象
            TenantryEntity tenantryEntity = tenantryDao.findById(id);
            if (tenantryEntity != null) {
                // 创建条件构造器用于查询企业详情
                QueryWrapper <CompanyEntity> wrapper = new QueryWrapper<>();
                // 根据企业id查询企业对象详情
                CompanyEntity companyEntity = companyDao.selectByAdminId(wrapper.eq("companyId", tenantryEntity.getCompanyId()));
                // 新建企业实体Bean对象
                CompanyBean companyBean = new CompanyBean();
                // 将租户合约实体类和企业实体类对象转换为实体Bean
                BeanUtils.copyProperties(tenantryEntity,tenantryBean);
                BeanUtils.copyProperties(companyEntity,companyBean);
                // 设置合约实体Bean中的企业对象
                tenantryBean.setCompanyBean(companyBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenantryBean;
    }

    /**
     * 用于新建合同和续约,该方法为同步方法,因为需要查询插入的数据的id,必须保证该记录是最新的
     * 故采用同步方法,保证不会有新的数据插入
     * @param tenantryBean 租户合同对象
     * @return 添加成功条数
     */
    private synchronized int addTenantry(TenantryBean tenantryBean,List <Integer> ids){
        int result =0;
        try {
            // 创建租户合约实体类对象
            TenantryEntity tenantryEntity = new TenantryEntity();
            // 设置合同开始时间
            tenantryBean.setTenantryStartTime(DateUtils.getDate());
            // 将租户合约实体Bean转换为实体类
            BeanUtils.copyProperties(tenantryBean,tenantryEntity);
            // 设置企业id
            tenantryEntity.setCompanyId(tenantryBean.getCompanyBean().getCompanyId());
            if(tenantryBean.getTenantryOriginalId()!=0){
               // 根据原合同id查询原合同
                TenantryEntity tenantry = tenantryDao.findById(tenantryBean.getTenantryOriginalId());
                // 设置合同时间为原合同结束时间
                tenantryEntity.setTenantryStartTime(tenantry.getTenantryEndTime());
            }
            // 设置合同状态为已生效
            tenantryEntity.setTenantryContractStatus("已生效");
            // 将租户合约添加到数据库
            result = tenantryDao.addNewTenantry(tenantryEntity);
            // 查询得到插入的合同的id
            // 创建条件构造器
            QueryWrapper <TenantryEntity> tenantryWrapper = new QueryWrapper<>();
            // 拼接条件
            tenantryWrapper.inSql("tenantryId","select max(tenantryId) from t_tenantry")
                           .select("tenantryId");
            int pactId = tenantryDao.selectByCondition(tenantryWrapper).getTenantryId();
            for (int i = 0; i < ids.size(); i++) {
                // 得到车位id
                int carportId = ids.get(i);
                // 创建条件构造器
                UpdateWrapper <ParkspaceEntity> wrapper = new UpdateWrapper<>();
                // 根据车位id将车位租户合约id改为当前合约id,将车位状态修改为已租赁
                // 根据租户合约id,将CBD车位状态修改为已租赁
                wrapper.eq("parkspaceId",carportId).set("parkspaceStatus","已租赁").set("tenantryId",pactId);
                // 传入修改条件，返回修改成功条数
                parkspaceDao.updateWarpper(null,wrapper);
            }
            // 新增一条企业计费信息及CBD计费信息
            CompanyBillEntity companyBillEntity = new CompanyBillEntity();
            // 设置账单关联的合同id
            companyBillEntity.setPactId(pactId);
            companyBillEntity.setCompanyBillPactType("租户合约");
            companyBillEntity.setCompanyBillMoney(tenantryBean.getTenantryDealPrice());
            companyBillEntity.setCompanyBillDate(DateUtils.getDate());
            companyBillEntity.setCompanyId(tenantryBean.getCompanyBean().getCompanyId());
            companyBillDao.insert(companyBillEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
