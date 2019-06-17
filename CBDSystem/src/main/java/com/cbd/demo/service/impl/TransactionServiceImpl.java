package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cbd.demo.bean.BillBean;
import com.cbd.demo.bean.TenantryBean;
import com.cbd.demo.bean.TransactionBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.dao.IBillDao;
import com.cbd.demo.dao.ICarportDao;
import com.cbd.demo.dao.ITransactionDao;
import com.cbd.demo.dao.IUserDao;
import com.cbd.demo.entity.*;
import com.cbd.demo.service.ITransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：杨强
 *  * @Desc ：类的介绍：出租交易记录业务接口实现类
 * @date ：Created in 2019/6/2 0002 18:58
 */
@Service
public class TransactionServiceImpl implements ITransactionService {

    @Autowired
    private ITransactionDao iTransactionDao;
    @Autowired
    private ICarportDao iCarportDao;
    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private IBillDao iBillDao;
    @Override
    /**
     * 添加出租交易记录，同时将该交易记录的车位改为“已租赁”，
     * 同时根据双方的id修改个人用户表的交易次数加一。
     * 同时生成一条出租交易记录账单，写入计费账单表。
     * @param transaction 出租交易记录对象
     * @return 是否成功，1表示成功。0表示失败
     */
    public int saveTransaction(TransactionBean transaction) {
        //保存结果
        int result=0;
        //  实体bean转entity
        TransactionEntity transactionEntity=new TransactionEntity();
        BeanUtils.copyProperties(transaction,transactionEntity);
        if(transaction.getLeaseUserUserBean()!=null){
            transactionEntity.setRentUserId(transaction.getRentUserUserBean().getUserId());
        }if(transaction.getLeaseUserUserBean()!=null){
            transactionEntity.setLeaseUserId(transaction.getLeaseUserUserBean().getUserId());
        }
      if(transaction.getBillBean()!=null){
          transactionEntity.setBillId(transaction.getBillBean().getBillId());
      }
        Integer carId=transactionEntity.getCarportId();
        //修改车位信息内容为已租赁
        CarportEntity carportEntity=new CarportEntity();
        carportEntity.setCarportStatus("已租赁");
        //创建条件修改车位为已租赁的wrapper
        UpdateWrapper<CarportEntity> carwrapper=new UpdateWrapper<>();
        carwrapper.eq("carportId",carId);

        try {
            iTransactionDao.saveTransaction(transactionEntity);

            try {
                result=iCarportDao.updateByWrapper(carportEntity,carwrapper);
                //第二步 修改双方交易次数
                //获取出租的人id
                if(transaction.getRentUserUserBean()!=null){
                    //获取出租人对象
                    Integer rentUserId=transaction.getRentUserUserBean().getUserId();
                    UserEntity rentUser=iUserDao.getById(rentUserId);
                    //修改次数
                    rentUser.setUserDeal(rentUser.getUserDeal()+1);
                    iUserDao.updateById(rentUser);
                }
                if(transaction.getLeaseUserUserBean()!=null){
                    Integer leaseUserId=transaction.getLeaseUserUserBean().getUserId();

                    //获取购买人对象
                    UserEntity leaseUser=iUserDao.getById(leaseUserId);
                    //修改交易次数
                    leaseUser.setUserDeal(leaseUser.getUserDeal()+1);
                    iUserDao.updateById(leaseUser);
                }


                //第三步 生成一条账单
                //创建一条交易账单
                BillEntity billEntity=new BillEntity();
                billEntity.setBillMoney(transaction.getTransactionPrice());
                        //获取当前时间
                SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                String date=df.format(new Date());
                SimpleDateFormat td=new SimpleDateFormat("HH:mm:ss");
                String time=td.format(new Date());
                billEntity.setBillDate(date);
                billEntity.setBillTime(time);
                billEntity.setBillType("个人租车账单");
                //billEntity缺少备注字段*****************************************************
                billEntity.setCarportId(transaction.getCarportId());
                if(transaction.getRentUserUserBean()!=null){

                    billEntity.setPartlyAId(transaction.getRentUserUserBean().getUserId());
                }
                if(transaction.getLeaseUserUserBean()!=null){
                    billEntity.setPartlyBId(transaction.getLeaseUserUserBean().getUserId());
                }
                result=iBillDao.insert(billEntity);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        return result;
    }

    /**
     * 根据账单id查询出租交易记录详情,
     * @param id  账单id
     * @return 账单对象
     */
    @Override
    public TransactionBean getTransaction(int id) {
        TransactionBean transactionBean=null;
        try {
            TransactionEntity transactionEntity=iTransactionDao.getTransaction(id);
            //  实体entity转bean
             transactionBean=new TransactionBean();
            BeanUtils.copyProperties(transactionEntity,transactionBean);
            //设置账单对象
            UpdateWrapper<BillEntity> carwrapper=new UpdateWrapper<>();
            carwrapper.eq("billId",transactionEntity.getBillId());
            BillEntity billEntity=iBillDao.selectOne(carwrapper);
            BillBean billBean=new BillBean();
            BeanUtils.copyProperties(billEntity,billBean);
            transactionBean.setBillBean(billBean);
            //设置出租者对象
            UserEntity RentEntity=iUserDao.getById(transactionEntity.getRentUserId());
            UserEntity LeasetEntity=iUserDao.getById(transactionEntity.getLeaseUserId());
            UserBean userBean=new UserBean();
            UserBean userBean1=new UserBean();
            BeanUtils.copyProperties(RentEntity,userBean);
            BeanUtils.copyProperties(LeasetEntity,userBean1);

            transactionBean.setRentUserUserBean(userBean);
            transactionBean.setLeaseUserUserBean(userBean1);
            return transactionBean;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactionBean;
    }

    @Override
    public TransactionBean getByCarport(int carportId) {
        // 创建实体Bean对象
        TransactionBean transactionBean = new TransactionBean();
        try {
            // 创建条件查询
            QueryWrapper <TransactionEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.inSql("transactionId","select max(transactionId) from t_transaction WHERE carportId = "+carportId);
            // 获得交易记录对象
            TransactionEntity transactionEntity = iTransactionDao.getByCondition(queryWrapper);
            if(transactionEntity!=null){
                BeanUtils.copyProperties(transactionEntity,transactionBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionBean;
    }
}
