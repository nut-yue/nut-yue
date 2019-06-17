package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.ComplaintBean;
import com.cbd.demo.entity.ComplaintEntity;

/**
 * @InterfaceName : IComplaintService
 * @Date ：2019/5/28 14:24
 * @Desc ：接口的介绍： 投诉业务接口
 * @author： 王佳伟
 * @change：峗权（增加了根据账单ID查询投诉对象方法）
 */
public interface IComplaintService {
    /**
     * 根据个人用户id查看该用户发起投诉的投诉信息
     * 如果起始时间为null，则只进行结束时间条件分页发现。
     * 如果结束时间为null，则只进行起始时间条件分页查询。
     * 如果起始时间和结束时间为null，则不进行条件查询。
     * 如果起始时间和结束时间都不为null，则进行完整的条件查询。
     * @param id 用户id
     * @param currentPage 当前页
     * @param pageSize 显示条数
     * @param oldDate 起始时间
     * @param newDate 结束时间
     * @return ComplaintBean类型的分页对象
     */
    IPage<ComplaintBean> findComplaintAll(int id, int currentPage, int pageSize,
                                            String oldDate, String newDate);

    /**
     * 查看单个投诉记录，同时根据投诉双方id查看双方的姓名
     * @param id 投诉记录id
     * @return  ComplaintBean类型的投诉记录对象
     */
    ComplaintBean getComplaint(int id);
    /**
     * 添加投诉，将投诉状态设置为“审核中”，再添加
     * @param complaint 投诉对象
     * @return 是否成功，失败返回0，成功返回1
     */
    int saveComplaint(ComplaintBean complaint);
    /**
     * 管理员修改投诉信息
     * @param complaint 投诉信息对象
     * @return 是否成功，失败返回0，成功返回1
     */
    int updateComplaint(ComplaintBean complaint);

    /**
     * 根据账单ID查询投诉对象
     * @param billId 账单ID
     * @return ComplaintBean对象
     */
    ComplaintBean getComplaintBean(int billId);

}
