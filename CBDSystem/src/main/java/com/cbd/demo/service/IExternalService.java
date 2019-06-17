package com.cbd.demo.service;/**
 * Created by 123 on 2019/5/28.
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.ExternalBean;
import com.cbd.demo.bean.ParkspaceBean;
import com.cbd.demo.entity.ExternalEntity;

import java.util.List;

/**
 * @ClassName : IExternalService
 * @Date ：2019/5/28 15:27
 * @Desc ：接口的介绍：外部合约的业务逻辑接口
 * @author：作者： 胡平
 * @change: 峗权（将返回的分页实体bean对象改为entity）
 */
public interface IExternalService {

    /**（添加合同）
     * 业务一： 添加外部合约的实体bean
     * 业务二： 批量添加cdb车位，调用IParkspaceService的批量的添加车位的方法
     * 业务三：externalContractStatus字段默认是 已生效, OriginalBean的默认是null
     * 业务四：计费系统的关联
     * @param externalBean 外部合约的实体类
     * @param parkspaceBeans 车位的list集合
     * @return int 成功返回影响的行数、失败返回0
     */
    int saveExternal(ExternalBean externalBean, List<ParkspaceBean> parkspaceBeans);

    /**（合同的续约）
     * 设置一个员id
     *  也是添加合约
     * @param externalBean 新的外部合约的实体类
     * @return
     */
    int contractExternal(ExternalBean externalBean);

    /**（合同的解约）
     *  业务一： 调用IParkspaceService的方法，修改cdb的状态
     *  业务二： 设置externalContractStatus字段为 失效
     *  业务三：计费系统的
     * @param externalBean
     * @return
     */
    int cancelExternal(ExternalBean externalBean);

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
    IPage<ExternalEntity> listExternal(int currentPage, int pageSize,
                                       String startDate, String endStartDate,
                                       String externalContractStatus);


    /** （通过id查询）
     * @param id  外部合约的externalId
     * @return 外部合约的实体bean
     */
    ExternalBean getExternalByExternalId(int id);


    
}
