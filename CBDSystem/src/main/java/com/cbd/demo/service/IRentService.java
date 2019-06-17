package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.RentBean;
import com.cbd.demo.entity.RentEntity;

/**
 * @InterfaceName : IRentService
 * @Date ：2019/5/28 14:24
 * @Desc ：接口的介绍： 出租信息业务接口
 * @author：岳超
 */
public interface IRentService {

    /**
     * 新增出租信息,业务场景是：管理员通过车位审核之后，选择发布招租，填写招租信息后，点击发布，
     * 此时向数据库表中添加一条出租信息记录，用于空闲出租车位展示使用
     * @param rent 出租信息对象
     * @return 添加记录数
     */
    int insertRent(RentBean rent);

    /**
     * 根据出租信息id查看出租记录详细信息,即在出租车位商城中选择某一个出租车位查看车位详细情况
     * @param rentId 出租记录id
     * @return 出租记录
     */
    RentBean getRent(int rentId);

    /**
     * 查询所有出租信息,按车位地址模糊查询，即车位商城中所有的出租车位信息
     * @param carportAddress 车位地址
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return 出租信息分页对象
     */
    IPage<RentBean> listRent(Integer userId,String carportAddress, int currentPage, int pageSize);
}
