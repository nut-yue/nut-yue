package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.ExternalBean;
import com.cbd.demo.bean.ParkspaceBean;
import com.cbd.demo.bean.TenantryBean;
import com.cbd.demo.dao.ICompanyBillDao;
import com.cbd.demo.dao.IExternalDao;
import com.cbd.demo.dao.IParkspaceDao;
import com.cbd.demo.entity.CompanyBillEntity;
import com.cbd.demo.entity.ExternalEntity;
import com.cbd.demo.entity.ParkspaceEntity;
import com.cbd.demo.service.IExternalService;
import com.cbd.demo.util.DateUtils;
import com.cbd.demo.util.MoneyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : ExternalServiceImpl
 * @Date ：2019/6/2 10:07
 * @Desc ：类的介绍：外部合约的业务逻辑接口实现类
 * @author：作者：峗权
 */
@Service
public class ExternalServiceImpl implements IExternalService {
    /**注入external的dao*/
    @Autowired
    private IExternalDao externalDao;
    /**注入parkspace的dao*/
    @Autowired
    private IParkspaceDao parkspaceDao;
    /**注入companyBill的dao*/
    @Autowired
    private ICompanyBillDao companyBillDao;

    //外部合约状态的默认值 (已生效）
    private final String externalContractStatus="已生效";
    //外部合约原合同对象的默认值 (null）
    private final ExternalBean originalBean=null;
    //企业账单类型默认值（外部合约）
    private final String companyBillPactType="外部合约";

    /**（添加合同）
     * 业务一： 添加外部合约的实体bean
     * 业务二： 批量添加cdb车位，调用IParkspaceService的批量的添加车位的方法
     * 业务三：externalContractStatus字段默认是 已生效, OriginalBean的默认是null
     * 业务四：计费系统的关联
     * @param externalBean 外部合约的实体类
     * @param parkspaceBeans 车位的list集合
     * @return int 成功返回影响的行数、失败返回0
     */
    @Override
    public int saveExternal(ExternalBean externalBean, List<ParkspaceBean> parkspaceBeans) {
        int num = 0;
        int lastId = 0;
        // 1 健壮性判断
        if(externalBean==null){
            return 0;
        }
        // 2 实体bean转entity
        ExternalEntity externalEntity = new ExternalEntity();
        BeanUtils.copyProperties(externalBean,externalEntity);

        // 3 设置entity中特殊字段
        externalEntity.setExternalContractStatus(externalContractStatus);
        try {
             num = externalDao.insert(externalEntity);
             lastId =externalDao.getInsertId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4 批量添加cdb车位
        if(parkspaceBeans!=null){
            for (ParkspaceBean parkspaceBean : parkspaceBeans) {
                ParkspaceEntity parkspaceEntity = new ParkspaceEntity();
                BeanUtils.copyProperties(parkspaceBean,parkspaceEntity);
                //设置entity中的特殊字段
                parkspaceEntity.setExternalId(lastId);
                try {
                    parkspaceDao.insert(parkspaceEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 5 计费系统的关联（添加）
        CompanyBillEntity companyBillEntity = new CompanyBillEntity();
        companyBillEntity.setCompanyBillDate(DateUtils.getDate());
        companyBillEntity.setPactId(lastId);
        companyBillEntity.setCompanyBillMoney(externalEntity.getExternalPrice());
        companyBillEntity.setCompanyBillPactType(companyBillPactType);
        //设置另一个系统的ID，用0表示
        companyBillEntity.setCompanyId(0);
        companyBillDao.insert(companyBillEntity);

        return num;
    }

    /**（合同的续约）
     * 设置一个员id
     *  也是添加合约
     * @param externalBean 新的外部合约的实体类
     * @return
     */
    @Override
    public int contractExternal(ExternalBean externalBean) {
        int num = 0;
        int lastId = 0;
        // 1 健壮性判断
        if(externalBean==null){
            return 0;
        }
        //设置原合同的状态已失效
        try {
            ExternalEntity external = externalDao.getById(externalBean.getOriginalBean().getExternalId());
            UpdateWrapper<ExternalEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("externalId",external.getExternalId())
                    .set("externalContractStatus","失效");
            externalDao.updateExternal(external,updateWrapper);

            // 2 实体bean转entity
            ExternalEntity externalEntity = new ExternalEntity();
            BeanUtils.copyProperties(externalBean,externalEntity);
            // 3 设置entity中特殊字段
            externalEntity.setExternalContractStatus(externalContractStatus);
            externalEntity.setOriginalId(externalBean.getOriginalBean().getExternalId());

            num = externalDao.insert(externalEntity);
            lastId = externalDao.getInsertId();

            QueryWrapper<ParkspaceEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("externalId",external.getExternalId());
            List<ParkspaceEntity> parkspaceList = parkspaceDao.selectList(wrapper);
            for (ParkspaceEntity parkspaceEntity : parkspaceList) {
                parkspaceEntity.setExternalId(lastId);
                parkspaceDao.updateById(parkspaceEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    /**（合同的解约）
     *  业务一： 调用IParkspaceService的方法，修改cdb的状态
     *  业务二： 设置externalContractStatus字段为 失效
     *  业务三：计费系统的
     * @param externalBean
     * @return
     */
    @Override
    public int cancelExternal(ExternalBean externalBean) {
        //调用parkspaceDao的方法，找到所有与该外部合约相关联的车位
        QueryWrapper<ParkspaceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("externalId",externalBean.getExternalId());
        try {
            List<Integer> parkId = new ArrayList<>();
            List<ParkspaceEntity> parkspaceList = parkspaceDao.selectList(queryWrapper);
            for (ParkspaceEntity parkspaceEntity : parkspaceList) {
                parkId.add(parkspaceEntity.getParkspaceId());
            }
            parkspaceDao.deleteList(parkId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int num = 0;
        //  健壮性判断
        if(externalBean==null){
            return 0;
        }
        //  实体bean转entity
        ExternalEntity externalEntity = new ExternalEntity();
        BeanUtils.copyProperties(externalBean,externalEntity);
        //  设置entity中特殊字段
        UpdateWrapper<ExternalEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("externalId",externalEntity.getExternalId())
        .set("externalContractStatus","失效");
        try {
            num = externalDao.updateExternal(externalEntity,wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //计费系统（先根据合同id查找到计费账单，再进行金额的计算，修改账单）
        QueryWrapper<CompanyBillEntity> query = new QueryWrapper<>();
        query.eq("pactId",externalBean.getExternalId());
        try {
            //获取第一个companyBillEntity
            CompanyBillEntity companyBillEntity = companyBillDao.getCompanyBill(query).get(0);
            //调用封装的计费方法
            int price = MoneyUtil.moneyCount(externalBean.getExternalffectivedate(),externalBean.getExternalDeadline(),externalBean.getExternalPrice());
            //修改账单的方法 ：companyBillDao
            UpdateWrapper<CompanyBillEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("companyBillId",companyBillEntity.getCompanyBillId())
                    .set("companyBillMoney",price)
                    .set("companyBillDate",DateUtils.getDate());
            companyBillDao.updateByCondition(companyBillEntity,updateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    /**（分页查询外部合同）
     *
     *  1、动态条件、externalffectivedate 的时间段
     *  2、externalContractStatus 已生效、未生效、已过期、失效（解约）
     * @param currentPage 当前页面
     * @param pageSize 每页显示条数
     * @param startDate 开始时间
     * @param endStartDate 结束时间
     * @param externalContractStatus 状态
     * @return IPage<ExternalEntity> 分页对象
     */
    @Override
    public IPage<ExternalEntity> listExternal(int currentPage, int pageSize, String startDate, String endStartDate, String externalContractStatus) {
        QueryWrapper<ExternalEntity> wrapper = new QueryWrapper();

        // 1 健壮性判断，并且给一些值付默认值
        if (currentPage < 0) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        // 2 动态拼接条件
        wrapper.ge(StringUtils.isNotEmpty(startDate),"externalffectivedate",startDate)
                .le(StringUtils.isNotEmpty(endStartDate),"externalDeadline",endStartDate)
                .eq(StringUtils.isNotEmpty(externalContractStatus),"externalContractStatus",externalContractStatus);
        //分页参数
        Page<ExternalEntity> page = new Page<>(currentPage,pageSize);

        try {
            return externalDao.listExternal(page,wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /** （通过id查询）
     * @param id  外部合约的externalId
     * @return 外部合约的实体bean
     */
    @Override
    public ExternalBean getExternalByExternalId(int id) {
        //得到externalEntity对象
        try {
            ExternalEntity externalEntity = externalDao.getById(id);

            //将externalEntity转换为externalBean
            ExternalBean externalBean = new ExternalBean();
            BeanUtils.copyProperties(externalEntity,externalBean);
            //设置externalBean的特有属性
            if(externalEntity.getOriginalId()!=null){
                ExternalEntity originalEntity = externalDao.getById(externalEntity.getOriginalId());
                ExternalBean originalBean = new ExternalBean();
                BeanUtils.copyProperties(originalEntity,originalBean);
                externalBean.setOriginalBean(originalBean);
            }
            return externalBean;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
