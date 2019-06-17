package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.ReservationBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.dao.*;
import com.cbd.demo.entity.*;
import com.cbd.demo.service.IReservationService;
import com.cbd.demo.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName : ReservationServiceImpl
 * @Date ：2019/6/1/0001 11:01
 * @Desc ：类的介绍：预约业务实现类
 * @author：张皓
 */
@Service
public class ReservationServiceImpl implements IReservationService {
    @Autowired
    private IReservationDao reservationDao;
    @Autowired
    private IMessageDao messageDao;
    @Autowired
    private ICarportDao carportDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IPersonalDao personalDao;
    @Autowired
    private IRentDao rentDao;
    @Autowired
    private ISaleDao saleDao;
    @Autowired
    private IBillDao billDao;
    @Autowired
    private ITransactionDao transactionDao;

    @Override
    public int insertReservation(ReservationBean reservation) {
        //设置预约类型为未处理
        reservation.setReservationDeal("未处理");
        //新增预约实体类
        ReservationEntity reservationEntity = new ReservationEntity();
        //新增一条短消息发送给被预约者
        MessageEntity messageEntity = new MessageEntity();
        //判断预约的类型是“租”或“买”
        if ("买".equals(reservation.getReservationType())) {
            //设置消息标题及类型
            messageEntity.setMessageTitle("你有一条买车预约");
            messageEntity.setMessageType("购车消息");
        } else {
            messageEntity.setMessageTitle("你有一条租车预约");
            messageEntity.setMessageType("租车消息");
        }
        //设置消息内容为留言内容
        messageEntity.setMessageContent(reservation.getReservationContent());
        //设置消息为未读
        messageEntity.setMessageIsRead(0);
        //设置消息发送时间为当前时间
        messageEntity.setMessageTime(DateUtils.getDateTime());
        if (reservation.getAppointerUserBean() != null) {
            //设置发送消息人为预约者
            messageEntity.setMessagePostUserId(reservation.getAppointerUserBean().getUserId());
            //设置预约信息中预约者ID
            reservationEntity.setAppointerUserId(reservation.getAppointerUserBean().getUserId());
        }
        if (reservation.getAppointedUserBean() != null) {
            //设置发送消息给被预约者
            messageEntity.setMessageGetUserId(reservation.getAppointedUserBean().getUserId());
            //设置预约信息中被预约者ID
            reservationEntity.setAppointedUserId(reservation.getAppointedUserBean().getUserId());
        }
        if (reservation.getCarportBean() != null) {
            Integer carportId = reservation.getCarportBean().getCarportId();
            //设置预约信息中车位ID
            reservationEntity.setCarportId(carportId);

        }
        //将预约Bean拷贝至预约实体类
        BeanUtils.copyProperties(reservation, reservationEntity);
        int i = 0;
        try {
            //新增短消息
            int insert = messageDao.insert(messageEntity);
            //新增预约信息
            i = reservationDao.insert(reservationEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public synchronized int updateReservation(ReservationBean reservation) {
        int i = 0;
        ReservationEntity reservationEntity = new ReservationEntity();
        //将预约Bean拷贝至预约实体类
        BeanUtils.copyProperties(reservation, reservationEntity);
        int postUserId = 0;
        int getUserId = 0;
        //得到车位Id
        Integer carportId = 0;
        //得到出租车位价格
        int rentPrice = 0;
        //得到出售车位价格
        int salePrice = 0;
        if (reservation.getAppointerUserBean() != null) {
            //设置预约信息中预约者ID
            reservationEntity.setAppointerUserId(reservation.getAppointerUserBean().getUserId());
            //得到预约者Id
            getUserId = reservation.getAppointerUserBean().getUserId();
        }
        if (reservation.getAppointedUserBean() != null) {
            //设置预约信息中被预约者ID
            reservationEntity.setAppointedUserId(reservation.getAppointedUserBean().getUserId());
            //得到被预约者Id
            postUserId = reservation.getAppointedUserBean().getUserId();
        }
        if (reservation.getCarportBean() != null) {
            //设置预约信息中车位ID
            reservationEntity.setCarportId(reservation.getCarportBean().getCarportId());
            carportId = reservation.getCarportBean().getCarportId();
        }
        //修改构造器
        UpdateWrapper<ReservationEntity> updateWrapper = new UpdateWrapper<>();
        UpdateWrapper<ReservationEntity> updateWrapper1 = new UpdateWrapper<>();
        //判断处理内容
        if ("同意预约".equals(reservation.getReservationDeal())) {
            //设置构造器条件，根据预约信息ID设置处理内容为已同意
            updateWrapper.set("reservationDeal", "已同意").eq("reservationId", reservation.getReservationId());
            //设置构造器条件，根据车位Id设置处理内容为已拒绝
            updateWrapper1.set("reservationDeal", "已拒绝").eq("carportId", carportId);
            //新增一条短消息发送给预约者
            MessageEntity messageEntity = new MessageEntity();
            //判断预约的类型是“租”或“买”
            if ("买".equals(reservation.getReservationType())) {
                //设置消息标题及类型
                messageEntity.setMessageTitle("你的买车预约已通过");
                messageEntity.setMessageType("购车消息");
                try {
                    //根据车位id得到出售价格
                    IPage<SaleEntity> iPage = new Page<>(1, 1);
                    QueryWrapper<SaleEntity> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("carportId", carportId);
                    IPage<SaleEntity> iPage1 = saleDao.findAllSaleInfo(iPage, queryWrapper);
                    if (iPage1.getRecords().get(0) != null) {
                        salePrice = iPage1.getRecords().get(0).getSalePrice();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //创建个人合同对象
                PersonalEntity personalEntity = new PersonalEntity();
                personalEntity.setPersonalContractNum(System.currentTimeMillis() + "");
                personalEntity.setPersonalPrice(salePrice);
                personalEntity.setPersonalDate(DateUtils.getDate());
                personalEntity.setPersonalBuyIsSigning("未签约");
                personalEntity.setPersonalSellIsSigning("未签约");
                personalEntity.setSellUserId(postUserId);
                personalEntity.setBuyUserId(getUserId);
                personalEntity.setCarportId(carportId);
                //设置消息内容为留言内容
                messageEntity.setMessageContent("查看合同");
                try {
                    personalDao.insert(personalEntity);
                    //出售预约同意后，改为已预约
                    CarportEntity carport = carportDao.getCarport(carportId);
                    UpdateWrapper<CarportEntity> carWrapper = new UpdateWrapper<>();
                    carWrapper.set("carportStatus", "已预约").eq("carportId", carportId);
                    carportDao.updateByWrapper(null, carWrapper);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                messageEntity.setMessageTitle("你的租车预约已通过");
                messageEntity.setMessageType("租车消息");
                String rentEndDate = null;
                try {
                    //出售预约同意后，改为已预约
                    CarportEntity carport = carportDao.getCarport(carportId);
                    UpdateWrapper<CarportEntity> carWrapper = new UpdateWrapper<>();
                    carWrapper.set("carportStatus", "已出租").eq("carportId", carportId);
                    carportDao.updateByWrapper(null, carWrapper);
                    //根据车位id得到出售价格
                    IPage<RentEntity> iPage = new Page<>(1, 1);
                    QueryWrapper<RentEntity> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("carportId", carportId);
                    IPage<RentEntity> iPage1 = rentDao.selectPage(iPage, queryWrapper);
                    if (iPage1.getRecords().get(0) != null) {
                        rentPrice = iPage1.getRecords().get(0).getRentPrice();
                        rentEndDate = iPage1.getRecords().get(0).getRentEndDate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //设置消息内容为留言内容
                messageEntity.setMessageContent("查看合同");
                //设置计费账单表
                BillEntity billEntity = new BillEntity();
                billEntity.setBillDate(DateUtils.getDate());
                billEntity.setBillTime(DateUtils.getTime());
                billEntity.setBillMoney(rentPrice);
                billEntity.setBillType("个人租车账单");
                billEntity.setCarportId(carportId);
                billEntity.setPartlyAId(postUserId);
                billEntity.setPartlyBId(getUserId);
                int billId = 0;
                try {
                    billDao.insert(billEntity);
                    // 创建条件构造器
                    QueryWrapper<BillEntity> tenantryWrapper = new QueryWrapper<>();
                    // 拼接条件
                    tenantryWrapper.inSql("tenantryId", "select max(tenantryId) from t_tenantry")
                            .select("tenantryId");
                    billId = billDao.selectOne(tenantryWrapper).getBillId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //设置出租交易记录
                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setCarportId(carportId);
                transactionEntity.setTransactionTime(DateUtils.getDate());
                transactionEntity.setTransactionEndTime(rentEndDate);
                transactionEntity.setTransactionPrice(rentPrice);
                transactionEntity.setBillId(billId);
                transactionEntity.setRentUserId(postUserId);
                transactionEntity.setLeaseUserId(getUserId);
                try {
                    transactionDao.saveTransaction(transactionEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //设置消息为未读
            messageEntity.setMessageIsRead(0);
            //设置消息发送时间为当前时间
            messageEntity.setMessageTime(DateUtils.getDateTime());
            //设置发送消息人为被预约者
            messageEntity.setMessagePostUserId(postUserId);
            //设置发送消息给预约者
            messageEntity.setMessageGetUserId(getUserId);
            try {
                //新增短消息
                int insert = messageDao.insert(messageEntity);
                //先根据车位Id设置所有处理内容为已拒绝
                reservationDao.update(null, updateWrapper1);
                //再根据预约信息ID设置处理内容为已同意
                i = reservationDao.update(null, updateWrapper);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("拒绝预约".equals(reservation.getReservationDeal())) {
            //设置构造器条件，根据预约信息ID设置处理内容为已拒绝
            updateWrapper.set("reservationDeal", "已拒绝").eq("reservationId", reservation.getReservationId());
            try {
                //先根据预约信息ID设置处理内容为已拒绝
                i = reservationDao.update(null, updateWrapper);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    @Override
    public IPage<ReservationBean> listSelfReservation(int appointerUserId, String reservationContent, int currentPage, int pageSize) {
        //创建一个分页对象，传入开始页数及每页显示数量
        IPage<ReservationEntity> iPage = new Page<>(currentPage, pageSize);
        //创建一个预约Bean分页对象
        IPage<ReservationBean> reservationBeanIPage = new Page<>(currentPage, pageSize);
        //创建一个条件构造器
        QueryWrapper<ReservationEntity> queryWrapper = new QueryWrapper<>();
        //根据预约者Id，动态查询预约内容,放入条件构造器
        queryWrapper.eq("appointerUserId", appointerUserId)

                .like(StringUtils.isNotEmpty(reservationContent), "reservationContent", reservationContent);
        IPage<ReservationEntity> reservationEntityIPage = null;
        try {
            //根据分页对象和条件构造器得到相关分页集合
            reservationEntityIPage = reservationDao.selectPage(iPage, queryWrapper);
            //从分页对象里取出预约entity集合
            List<ReservationEntity> records = reservationEntityIPage.getRecords();
            System.out.println(records + "--------------------------------");
            //创建一个预约Bean集合
            List<ReservationBean> reservationBeanList = new ArrayList<>();
            //遍历预约entity集合
            for (ReservationEntity record : records) {
                ReservationBean reservationBean = new ReservationBean();
                BeanUtils.copyProperties(record, reservationBean);
                //------------------封装车位Bean--------------
                //得到每一个车位ID
                Integer carportId = record.getCarportId();
                //得到每一个车位entity
                CarportEntity carport = carportDao.getCarport(carportId);
                //将车位entity拷贝至车位Bean
                CarportBean carportBean = new CarportBean();
                BeanUtils.copyProperties(carport, carportBean);
                //将车位Bean放至每一个预约Bean
                reservationBean.setCarportBean(carportBean);
                //------------------封装预约者Bean----------------
                //根据预约者ID得到预约者entity
                UserEntity appointerEntity = userDao.getById(appointerUserId);
                //将预约者entity拷贝至预约者Bean
                UserBean userBean = new UserBean();
                BeanUtils.copyProperties(appointerEntity, userBean);
                //将预约者Bean放入预约信息Bean
                reservationBean.setAppointerUserBean(userBean);
                //------------------封装被预约者Bean--------------
                //根据被预约者ID得到预约者entity
                Integer appointedUserId = record.getAppointedUserId();
                UserEntity appointedEntity = userDao.getById(appointedUserId);
                //将被预约者entity拷贝至被预约者Bean
                UserBean userBean1 = new UserBean();
                BeanUtils.copyProperties(appointedEntity, userBean1);
                //将被预约者Bean放入预约信息Bean
                reservationBean.setAppointedUserBean(userBean1);
                //再将每一个预约Bean放入预约集合
                reservationBeanList.add(reservationBean);
            }
            //将预约集合放入预约分页对象
            reservationBeanIPage.setRecords(reservationBeanList);
            reservationBeanIPage.setTotal(reservationEntityIPage.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservationBeanIPage;
    }

    @Override
    public IPage<ReservationBean> listReservation(int appointedUserId, String reservationContent, int currentPage, int pageSize) {
        //创建一个分页对象，传入开始页数及每页显示数量
        IPage<ReservationEntity> iPage = new Page<>(currentPage, pageSize);
        //创建一个预约Bean分页对象
        IPage<ReservationBean> reservationBeanIPage = new Page<>(currentPage, pageSize);
        //创建一个条件构造器
        QueryWrapper<ReservationEntity> queryWrapper = new QueryWrapper<>();
        //根据预约者Id，动态查询预约内容,放入条件构造器
        queryWrapper.eq("appointedUserId", appointedUserId)
                .eq("reservationDeal", "未处理")
                .like(StringUtils.isNotEmpty(reservationContent), "reservationContent", reservationContent);
        IPage<ReservationEntity> reservationEntityIPage = null;
        try {
            //根据分页对象和条件构造器得到相关分页对象
            reservationEntityIPage = reservationDao.selectPage(iPage, queryWrapper);
            //从分页对象里取出预约entity集合
            List<ReservationEntity> records = reservationEntityIPage.getRecords();
            //创建一个预约Bean集合
            List<ReservationBean> reservationBeanList = new ArrayList<>();
            //遍历预约entity集合
            for (ReservationEntity record : records) {
                ReservationBean reservationBean = new ReservationBean();
                BeanUtils.copyProperties(record, reservationBean);
                //------------------封装车位Bean--------------
                //得到每一个车位ID
                Integer carportId = record.getCarportId();
                //得到每一个车位entity
                CarportEntity carport = carportDao.getCarport(carportId);
                //将车位entity拷贝至车位Bean
                CarportBean carportBean = new CarportBean();
                BeanUtils.copyProperties(carport, carportBean);
                //将车位Bean放至每一个预约Bean
                reservationBean.setCarportBean(carportBean);
                //------------------封装预约者Bean----------------
                //得到预约者ID
                Integer appointerUserId = record.getAppointerUserId();
                //根据预约者ID得到预约者entity
                UserEntity appointerEntity = userDao.getById(appointerUserId);
                //将预约者entity拷贝至预约者Bean
                UserBean userBean = new UserBean();
                BeanUtils.copyProperties(appointerEntity, userBean);
                //将预约者Bean放入预约信息Bean
                reservationBean.setAppointerUserBean(userBean);
                //------------------封装被预约者Bean--------------
                //根据被预约者ID得到预约者entity
                UserEntity appointedEntity = userDao.getById(appointedUserId);
                //将被预约者entity拷贝至被预约者Bean
                UserBean userBean1 = new UserBean();
                BeanUtils.copyProperties(appointedEntity, userBean1);
                //将被预约者Bean放入预约信息Bean
                reservationBean.setAppointedUserBean(userBean1);

                //再将每一个预约Bean放入预约集合
                reservationBeanList.add(reservationBean);
            }
            //将预约集合放入预约分页对象
            reservationBeanIPage.setRecords(reservationBeanList);
            reservationBeanIPage.setTotal(reservationEntityIPage.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservationBeanIPage;
    }

    @Override
    public ReservationBean getReservation(int reservationId) {
        ReservationBean reservationBean = new ReservationBean();
        try {
            //根据预约信息ID得到预约信息实体类
            ReservationEntity reservationEntity = reservationDao.selectById(reservationId);
            //将预约信息实体类拷贝至为预约bean
            BeanUtils.copyProperties(reservationEntity, reservationBean);
            //根据预约者ID得到预约者实体类
            UserEntity userEntity = userDao.getById(reservationEntity.getAppointerUserId());
            //将预约者实体类拷贝至预约者Bean
            UserBean userBean = new UserBean();
            BeanUtils.copyProperties(userEntity, userBean);
            //将预约者Bean放入预约信息Bean
            reservationBean.setAppointerUserBean(userBean);

            //将被预约者信息放入预约信息Bean中
            UserEntity userEntity1 = userDao.getById(reservationEntity.getAppointedUserId());
            UserBean userBean1 = new UserBean();
            BeanUtils.copyProperties(userEntity1, userBean1);
            reservationBean.setAppointedUserBean(userBean1);

            //将车辆信息放入预约信息Bean中
            CarportEntity carport = carportDao.getCarport(reservationEntity.getCarportId());
            CarportBean carportBean = new CarportBean();
            BeanUtils.copyProperties(carport, carportBean);
            reservationBean.setCarportBean(carportBean);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservationBean;
    }
}
