package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.*;
import com.cbd.demo.dao.IBillDao;
import com.cbd.demo.dao.ICarportDao;
import com.cbd.demo.dao.IPersonalDao;
import com.cbd.demo.dao.IUserDao;
import com.cbd.demo.entity.*;
import com.cbd.demo.service.IBillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：杨强
 * @date ：Created in 2019/6/2 0002 18:57
 */
@Service
public class BillServiceImpl implements IBillService {

    @Autowired
    private IBillDao iBillDao;
    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private ICarportDao iCarportDao;
    /**
     * 根据登录用户的id分页查询，同时根据分页查询出来的
     * 如果起始时间为null，则只进行结束时间条件分页发现。
     * 如果结束时间为null，则只进行起始时间条件分页查询。
     * 如果起始时间和结束时间为null，则不进行条件查询。
     * 如果起始时间和结束时间都不为null，则进行完整的条件查询。
     * 如果账单类型不为null，则进行账单类型精确查询
     */
    @Override
    public IPage<BillEntity> getBillAll(int id, int currentPage, int pageSize, String oldDate, String newDate, String billType) {
        IPage<BillEntity> page = new Page<>(currentPage,pageSize);
        QueryWrapper<BillEntity> wrapper= new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(billType),"billType",billType)
                .eq("partlyAId",id)
                .gt(StringUtils.isNotEmpty(oldDate),"billDate",oldDate)
                .lt(StringUtils.isNotEmpty(newDate),"billDate",newDate)
                .or()
                .eq(StringUtils.isNotEmpty(billType),"billType",billType)
                .eq("partlyBId",id)
                .gt(StringUtils.isNotEmpty(oldDate),"billDate",oldDate)
                .lt(StringUtils.isNotEmpty(newDate),"billDate",newDate)
              ;
        try {
          page=iBillDao.selectPage(page,wrapper);
            List<BillEntity> records = page.getRecords();
            for (BillEntity buill: records) {
                if(buill.getPartlyBId() == id) {
                    buill.setBillMoney(-buill.getBillMoney());
                    //System.out.println(buill.getBillMoney());
                }
            }
            System.out.println(page);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DealCountBean getMoney(int id) {
        DealCountBean dealCountBean=null;
        QueryWrapper<BillEntity> wrapperA = new QueryWrapper<>();
        QueryWrapper<BillEntity> wrapperB = new QueryWrapper<>();
        wrapperA.eq("partlyAId",id);
        wrapperB.eq("partlyBId",id);

        //A为负数 B是正数
        //收入钱
        int getMoney=0;
        //支出钱
        int payMoney=0;
        try {
            //查询所有出租人是id的记录
            List<BillEntity> listRentA=iBillDao.selectList(wrapperA);
            List<BillEntity> listRentB=iBillDao.selectList(wrapperB);
            dealCountBean=new DealCountBean();
            dealCountBean.setDealSum(listRentA.size()+listRentB.size());
           // System.out.println(listRentA.size());

            for (int i=0;i<listRentA.size();i++){
                BillEntity billEntityA=listRentA.get(i);
                getMoney+=billEntityA.getBillMoney();
            };
            for (int i=0;i<listRentB.size();i++){
                BillEntity billEntityB=listRentB.get(i);
                payMoney+=billEntityB.getBillMoney();
            };
            //设置交易笔数

            //设置收入金额
            dealCountBean.setGetMoney(getMoney);
            //设置支出金额
            dealCountBean.setPayMoney(payMoney);
            //设置总金额
            dealCountBean.setGetMoneySum(getMoney-payMoney);
            return dealCountBean;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dealCountBean;
    }

    /**
     * 根据计费账单id获得计费账单对象，
     * 根据账单对象中的账单类型属性判断是什么账单类型，
     * 1、如果账单类型是“购车账单”，则用账单id外键查询个人买卖车合同表，
     * 获得购买方id和出售方id，再根据这两个id查询个人用户表中的用户真实姓名。
     *
     * 2、如果账单类型是“个人租车账单”，则用账单id外键查询出租交易记录表，
     * 获得出租方和被出租方的id，再根据这两个id查询个人用户表中的用户真实姓名。
     * @param id 计费账单id
     * @return 计费账单对象
     */
    @Override
    public BillBean getBill(int id) {
        BillBean billBean=new BillBean();
        QueryWrapper<BillEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("billId",id);
        try {
            BillEntity billEntity=iBillDao.selectOne(wrapper);
            BeanUtils.copyProperties(billEntity,billBean);
           // System.out.println("11111111111111"+billBean);
            UserEntity sel=new UserEntity();
            UserEntity pay=new UserEntity();
                //封装卖家的信息
                sel=iUserDao.getById(billEntity.getPartlyAId());
                UserBean userBeanA=new UserBean();
                BeanUtils.copyProperties(sel,userBeanA);

                //封装买家的信息
                pay=iUserDao.getById(billEntity.getPartlyBId());
                UserBean userBeanB=new UserBean();
                BeanUtils.copyProperties(pay,userBeanB);
                //封装carBean
                CarportEntity carportEntity=new CarportEntity();
                CarportBean carportBean=new CarportBean();
                carportEntity=iCarportDao.getCarport(billEntity.getCarportId());
                BeanUtils.copyProperties(carportEntity,carportBean);

                //封装bean
                billBean.setCarportBean(carportBean);
                billBean.setPartlyA(userBeanA);
                billBean.setPartlyB(userBeanB);
                //System.out.println("+++++++++++++++"+billBean);
                return billBean;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return billBean;
    }


}
