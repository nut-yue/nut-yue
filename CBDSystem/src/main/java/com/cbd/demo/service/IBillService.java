package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.BillBean;
import com.cbd.demo.bean.DealCountBean;
import com.cbd.demo.entity.BillEntity;

import java.util.List;

/**
 * @InterfaceName : IBillService
 * @Date ：2019/5/28 14:24
 * @Desc ：接口的介绍： 计费账单业务接口
 * @author： 王佳伟
 */
public interface IBillService {
    /**
     * 根据登录用户的id分页查询，同时根据分页查询出来的
     * 如果起始时间为null，则只进行结束时间条件分页发现。
     * 如果结束时间为null，则只进行起始时间条件分页查询。
     * 如果起始时间和结束时间为null，则不进行条件查询。
     * 如果起始时间和结束时间都不为null，则进行完整的条件查询。
     * 如果账单类型不为null，则进行账单类型精确查询
     *
     * @param id 所属人id
     * @param currentPage 当前页
     * @param pageSize 每页显示的条数
     * @param oldDate 起始时间
     * @param newDate 结束时间
     * @return BillBean类型的计费账单对象的分页对象
     */
     IPage<BillEntity> getBillAll(int id, int currentPage, int pageSize,
                                  String oldDate, String newDate, String billType);
    /**
     * 根据所属人id，查询该用户的消费记录，用消费记录计算出总笔数、
     * 支出金额、收入金额、总金额。
     * @param id
     * @return
     */
    DealCountBean getMoney(int id);
    /**
     * 根据计费账单id获得计费账单对象，
     * 根据账单对象中的账单类型属性判断是什么账单类型，
     * 1、如果账单类型是“购车账单”，则用账单id外键查询个人买卖车合同表，
     * 获得购买方id和出售方id，再根据这两个id查询个人用户表中的用户真实姓名。
     *
     * 2、如果账单类型是“个人租车账单”，则用账单id外键查询出租交易记录表，
     * 获得出租方和被出租方的id，再根据这两个id查询个人用户表中的用户真实姓名。
     * @param id  账单id
     * @return 计费账单对象
     */
     BillBean getBill(int  id);

}
