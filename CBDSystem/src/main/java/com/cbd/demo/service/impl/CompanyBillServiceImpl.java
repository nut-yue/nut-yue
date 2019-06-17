package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.CompanyBillBean;
import com.cbd.demo.bean.DealCountBean;
import com.cbd.demo.bean.TenantryBean;
import com.cbd.demo.dao.ICompanyBillDao;
import com.cbd.demo.dao.IExternalDao;
import com.cbd.demo.dao.ITenantryDao;
import com.cbd.demo.entity.CompanyBillEntity;
import com.cbd.demo.entity.ExternalEntity;
import com.cbd.demo.entity.TenantryEntity;
import com.cbd.demo.service.ICompanyBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : CampanyBillController
 * @Date ：2019/6/3 10:00
 * @Desc ：企业账单实现类
 * @author：作者：刘划轩
 */
@Service
public class CompanyBillServiceImpl implements ICompanyBillService {
    @Autowired
    private ICompanyBillDao companyBillDao;
    @Autowired
    private ITenantryDao tenantryDao;
    @Autowired
    private IExternalDao externalDao;

    @Override
    public IPage<CompanyBillEntity> listCompanyBill(int currentPage, int pageSize, String type, String oldDate, String newDate) {
        QueryWrapper<CompanyBillEntity> wrapper=new QueryWrapper<CompanyBillEntity>();
        wrapper.eq(StringUtils.isNotEmpty(type),"companyBillPactType",type).ge(StringUtils.isNotEmpty(oldDate),"companyBillDate",oldDate).le(StringUtils.isNotEmpty(newDate),"companyBillDate",newDate);
        Page<CompanyBillEntity> page=new Page<CompanyBillEntity>(currentPage,pageSize);
        try {
            return companyBillDao.listCompanyBill(page,wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IPage<CompanyBillEntity> listCompanyBill(int id, int currentPage, int pageSize, String type, String oldDate, String newDate) {
        QueryWrapper<CompanyBillEntity> wrapper=new QueryWrapper<CompanyBillEntity>();
        wrapper.eq("companyId",id).eq(StringUtils.isNotEmpty(type),"companyBillPactType",type).ge(StringUtils.isNotEmpty(oldDate),"companyBillDate",oldDate).le(StringUtils.isNotEmpty(newDate),"companyBillDate",newDate);
        Page<CompanyBillEntity> page=new Page<CompanyBillEntity>(currentPage,pageSize);
        try {
            return companyBillDao.listCompanyBill(page,wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CompanyBillBean getCompanyBill(int id, String billType) {
        try {
            CompanyBillBean companyBillBean=new CompanyBillBean();
            if (billType.equals("租户合约")){
                TenantryEntity tenantryEntity=tenantryDao.findById(id);
                companyBillBean.setTenantryContractNum(tenantryEntity.getTenantryContractNum());
                companyBillBean.setTenantryDealPrice(tenantryEntity.getTenantryDealPrice());
                companyBillBean.setTenantryStartTime(tenantryEntity.getTenantryStartTime());
                companyBillBean.setTenantryEndTime(tenantryEntity.getTenantryEndTime());
                companyBillBean.setTenantryContractStatus(tenantryEntity.getTenantryContractStatus());

            }else {
                ExternalEntity externalEntity=externalDao.getById(id);
                companyBillBean.setTenantryContractNum(externalEntity.getExternalNo());
                companyBillBean.setTenantryDealPrice(externalEntity.getExternalPrice());
                companyBillBean.setTenantryStartTime(externalEntity.getExternalffectivedate());
                companyBillBean.setTenantryEndTime(externalEntity.getExternalDeadline());
                companyBillBean.setTenantryContractStatus(externalEntity.getExternalContractStatus());
            }
            return companyBillBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DealCountBean getMoney(int id) {
        DealCountBean dealCountBean=new DealCountBean();
        int num=0;
        //租户总金额
        int money=tenantryDao.getCountMoney(id);
        if (id==-1){
            //外部合同总金额
            try {
                int money2=externalDao.getgetCountMoney();
                num=tenantryDao.getSun(null)+externalDao.getConut();
                dealCountBean.setDealSum(num);
                dealCountBean.setPayMoney(money2);
                dealCountBean.setGetMoney(money);
                dealCountBean.setGetMoneySum(money-money2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            QueryWrapper<TenantryEntity> wrapper=new QueryWrapper<TenantryEntity>();
            wrapper.eq("companyId",id);
            num=tenantryDao.getSun(wrapper);
            dealCountBean.setPayMoney(money);
            dealCountBean.setGetMoney(0);
            dealCountBean.setGetMoneySum(money);
            dealCountBean.setDealSum(num);
        }
        return dealCountBean;
    }
}
