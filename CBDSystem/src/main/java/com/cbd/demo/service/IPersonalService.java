package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.PersonalBean;
import com.cbd.demo.entity.PersonalEntity;

/**
 * @ClassName : IPersonalService
 * @Date ：2019/5/28 15:26
 * @Desc ：接口的介绍： 个人买卖合同的业务逻辑接口
 * @author：作者：岳超
 */
public interface IPersonalService {

    /**
     * 当购买方收到出售方确认预约消息之后，可以点击购买，此时向数据库中添加一条双方签约未签约的合约
     * 记录，同时向双方发送一条消息，提示有一份未签约合同，提示签约
     * @param personal 个人合同对象
     * @return 添加成功记录条数，返回 0：添加失败 1：添加成功
     */
    int addPersonal(PersonalEntity personal);

    /**
     * 根据个人买卖合同id查看合同详细信息
     * @param personalId 个人合同id
     * @return 合同对象
     */
    PersonalBean getPersonal(int personalId);

    /**
     * 1.修改个人买卖合同买方是否签约和卖方是否签约状态,共享此方法
     * 2.当合约中的买卖双方签约状态均为已签约时，将车位状态改为已售出,删除车辆出售展示记录
     * 3.生成双方个人买卖交易账单，双方交易次数加1
     * 4.向双方发送交易成功消息提醒
     * @param personal 修改后的合同对象
     * @return 修改成功的记录条数，返回 1：修改成功 0：修改失败
     */
    int updateSelfPersonalSigning(PersonalEntity personal);


    /**
     * 1.修改个人买卖合同买方是否签约和卖方是否签约状态,共享此方法
     * 2.当合约中的买卖双方签约有一方未签约时，删除合同，修改车辆状态为待出售
     * 3.向双方发送交易失败消息提醒
     * @param personal 修改后的合同对象
     * @return 修改成功的记录条数，返回 1：修改成功 0：修改失败
     */
    int updatePersonalSigning(PersonalEntity personal);


    /**
     * 查询个人所有的合约，包括自己是买方和自己是卖方的合同,使用or进行查询
     * @param userId 操作者id
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return 个人合同集合分页对象
     */
    IPage<PersonalEntity> listPersonal(int userId, int currentPage, int pageSize);


    /**
     * 通过合同类型动态查询合同,如果查询一方，另一个id为0
     * @param buyUserId 购买方id
     * @param sellUserId 出售方id
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return 动态查询合同集合分页对象
     */
    IPage<PersonalEntity> listPersonalByCondition(int buyUserId, int sellUserId, int currentPage, int pageSize);

    /**
     * 查看所有个人合同
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return 个人合同分页对象
     */
    IPage<PersonalEntity> listAllPersonal(int currentPage, int pageSize);
}
