package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.TenantryBean;
import com.cbd.demo.bean.TransactionBean;

/**
 * @ClassName : TransactionService
 * @Date ：2019/5/28 15:31
 * @Desc ：类的介绍：出租交易记录业务接口
 * @author：作者：王佳伟
 */
public interface ITransactionService {
    /**
     * 添加出租交易记录，同时将该交易记录的车位改为“已租赁”，
     * 同时根据双方的id修改个人用户表的交易次数加一。
     * 同时生成一条出租交易记录账单，写入计费账单表。
     * @param transaction 出租交易记录对象
     * @return 是否成功，1表示成功。0表示失败
     */
    int saveTransaction(TransactionBean transaction);
    /**
     * 根据账单id查询出租交易记录详情,
     * @param id  账单id
     * @return 账单对象
     */
    TransactionBean getTransaction(int id);

    /**
     * 根据车位id查询车位信息
     * @param carportId 车位id
     * @return
     */
    TransactionBean getByCarport(int carportId);

}
