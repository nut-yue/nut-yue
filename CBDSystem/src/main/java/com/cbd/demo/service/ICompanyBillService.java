package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.CompanyBillBean;
import com.cbd.demo.bean.DealCountBean;
import com.cbd.demo.entity.CompanyBillEntity;

/**
 * @ClassName : companyBillService
 * @Date ：2019/5/30 18:48
 * @Desc ：类的介绍：企业计费账单业务接口
 * @author：作者：王佳伟
 */
public interface ICompanyBillService {
    /**
     * 管理员分页查询全部的cbd计费账单,
     * 如果状态、起始日期、结束日期，不为null，则进行条件查询
     *@param currentPage 当前页
     * @param pageSize 显示条数
     * @param type 账单类型
     * @param oldDate 起始日期
     * @param newDate 结束日期
     * @return 企业计费账单的分页对象
     */
    IPage<CompanyBillEntity> listCompanyBill(int currentPage, int pageSize,
                                             String type, String oldDate, String newDate);
    /**
     *
     * @param id 企业用户id
     * @param currentPage 当前页
     * @param pageSize 显示条数
     * @param type 账单类型
     * @param oldDate 起始时间
     * @param newDate 结束时间
     * @return 企业计费账单的分页对象
     */
    IPage<CompanyBillEntity> listCompanyBill(int id, int currentPage, int pageSize,
                                           String type, String oldDate, String newDate);

    /**
     * 根据企业计费账单id查找该企业计费账单对象，同时根据获得的外键id，
     * 查找对应的租户合同表，并封装企业计费账单对象中。
     * @param id 企业id
     * @return 企业计费账单的对象
     */
    CompanyBillBean getCompanyBill(int id,String billType);
    /**
     * 如果是cbd系统查询则企业id为-1，根据企业id，查询该企业消费记录，
     * 用消费记录计算出总笔数、支出金额、收入金额、总金额。
     * @param id 企业id
     * @return 企业计费账单对象
     */
    DealCountBean getMoney(int id);
}
