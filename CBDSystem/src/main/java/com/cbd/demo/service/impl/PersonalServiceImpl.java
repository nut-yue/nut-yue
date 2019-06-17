package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.PersonalBean;
import com.cbd.demo.dao.*;
import com.cbd.demo.entity.*;
import com.cbd.demo.service.IPersonalService;
import com.cbd.demo.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @ClassName : CompanyBillServiceImpl
 * @Date ：2019/6/3 10:00
 * @Desc ：个人买卖合同的业务逻辑实现类
 * @author：作者：刘划轩
 */
@Service
public class PersonalServiceImpl implements IPersonalService {
    @Autowired
    private IPersonalDao personalDao;
    @Autowired
    private IMessageDao messageDao;
    @Autowired
    private ICarportDao carportDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IBillDao billDao;
    @Autowired
    private ISaleDao saleDao;


    @Override
    public synchronized int addPersonal(PersonalEntity personal) {
        try {
            BillEntity billEntity=new BillEntity();
            billEntity.setBillDate(personal.getPersonalDate());
            billEntity.setBillTime(DateUtils.getTime());
            billEntity.setBillMoney(personal.getPersonalPrice());
            billEntity.setBillType("个人购车账单");
            billEntity.setBillRemark("");
            billEntity.setCarportId(personal.getCarportId());
            billEntity.setPartlyAId(personal.getSellUserId());
            billEntity.setPartlyBId(personal.getBuyUserId());
            billDao.insert(billEntity);
            // 查询得到插入的合同的id
            // 创建条件构造器
            QueryWrapper<BillEntity> tenantryWrapper = new QueryWrapper<BillEntity>();
            // 拼接条件
            tenantryWrapper.inSql("billId", "select max(billId) from t_bill").select("billId");
            int billId = billDao.selectOne(tenantryWrapper).getBillId();
            personal.setBillId(billId);
            personalDao.insert(personal);
            MessageEntity messageEntity1 = new MessageEntity();
            messageEntity1.setMessageTitle("您有一份合同需要签署");
            messageEntity1.setMessageContent("您有一份购车合同需要签署，请前往个人合同查看");
            messageEntity1.setMessageTime(DateUtils.getDateTime());
            messageEntity1.setMessageType("买卖车消息");
            messageEntity1.setMessageIsRead(0);
            messageEntity1.setMessagePostUserId(-1);
            messageEntity1.setMessageGetUserId(personal.getBuyUserId());
            MessageEntity messageEntity2 = new MessageEntity();
            messageEntity2.setMessageTitle("您有一份合同需要签署");
            messageEntity2.setMessageContent("您有一份购车合同需要签署，请前往个人合同查看");
            messageEntity2.setMessageTime(DateUtils.getDateTime());
            messageEntity2.setMessageType("买卖车消息");
            messageEntity2.setMessageIsRead(0);
            messageEntity2.setMessagePostUserId(-1);
            messageEntity2.setMessageGetUserId(personal.getSellUserId());
            messageDao.insert(messageEntity1);
            messageDao.insert(messageEntity2);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public PersonalBean getPersonal(int personalId) {
        try {
            PersonalEntity personalEntity = personalDao.selectById(personalId);
            PersonalBean personalBean = new PersonalBean();
            BeanUtils.copyProperties(personalEntity, personalBean);
            UserEntity sellUserBean=userDao.getById(personalEntity.getSellUserId());
            UserEntity buyUserUserBean=userDao.getById(personalEntity.getBuyUserId());
            CarportEntity carportEntity=carportDao.getCarport(personalEntity.getCarportId());
            personalBean.setSellUserBean(sellUserBean);
            personalBean.setBuyUserUserBean(buyUserUserBean);
            personalBean.setCarportBean(carportEntity);
            return personalBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateSelfPersonalSigning(PersonalEntity personal) {
        try {
            QueryWrapper<PersonalEntity> personalWrapper = new QueryWrapper<PersonalEntity>();
            personalWrapper.eq("personalId",personal.getPersonalId());
            personalDao.update(personal, personalWrapper);
            if (personal.getPersonalBuyIsSigning().equals("签约") && personal.getPersonalSellIsSigning().equals("签约")) {
                BillEntity billEntity=new BillEntity();
                billEntity.setBillDate(personal.getPersonalDate());
                billEntity.setBillTime(DateUtils.getTime());
                billEntity.setBillMoney(personal.getPersonalPrice());
                billEntity.setBillType("个人购车账单");
                billEntity.setBillRemark("");
                billEntity.setCarportId(personal.getCarportId());
                billEntity.setPartlyAId(personal.getSellUserId());
                billEntity.setPartlyBId(personal.getBuyUserId());
                billDao.insert(billEntity);
                // 查询得到插入的合同的id
                // 创建条件构造器
                QueryWrapper<BillEntity> tenantryWrapper = new QueryWrapper<BillEntity>();
                // 拼接条件
                tenantryWrapper.inSql("billId", "select max(billId) from t_bill").select("billId");
                int billId = billDao.selectOne(tenantryWrapper).getBillId();
                personal.setBillId(billId);
                personalDao.update(personal, personalWrapper);
                QueryWrapper<CarportEntity> wrapper = new QueryWrapper<CarportEntity>();
                wrapper.eq("carportId", personal.getCarportId());
                CarportEntity carportEntity=carportDao.getCarport(personal.getCarportId());
                carportEntity.setCarportStatus("已出售");
                carportDao.updateByWrapper(carportEntity,wrapper);
                saleDao.delSale(personal.getCarportId());
                UserEntity buyUser = userDao.getById(personal.getBuyUserId());
                buyUser.setUserDeal(buyUser.getUserDeal() + 1);
                userDao.updateById(buyUser);
                UserEntity sellUser = userDao.getById(personal.getSellUserId());
                sellUser.setUserDeal(sellUser.getUserDeal() + 1);
                userDao.updateById(sellUser);
                MessageEntity messageEntity1 = new MessageEntity();
                messageEntity1.setMessageTitle("您的购车合同完成");
                messageEntity1.setMessageContent("您的购车合同完成，请前往个人合同查看");
                messageEntity1.setMessageTime(DateUtils.getDateTime());
                messageEntity1.setMessageType("买卖车消息");
                messageEntity1.setMessageIsRead(0);
                messageEntity1.setMessagePostUserId(-1);
                messageEntity1.setMessageGetUserId(personal.getBuyUserId());
                MessageEntity messageEntity2 = new MessageEntity();
                messageEntity2.setMessageTitle("您的购车合同完成");
                messageEntity2.setMessageContent("您的购车合同完成，请前往个人合同查看");
                messageEntity2.setMessageTime(DateUtils.getDateTime());
                messageEntity2.setMessageType("买卖车消息");
                messageEntity2.setMessageIsRead(0);
                messageEntity2.setMessagePostUserId(-1);
                messageEntity2.setMessageGetUserId(personal.getSellUserId());
                messageDao.insert(messageEntity1);
                messageDao.insert(messageEntity2);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updatePersonalSigning(PersonalEntity personal) {
        try {
            QueryWrapper<CarportEntity> wrapper = new QueryWrapper<CarportEntity>();
            wrapper.eq("carportId",personal.getCarportId());
            CarportEntity carportEntity=carportDao.getCarport(personal.getCarportId());
            carportEntity.setCarportStatus("待出售");
            carportDao.updateByWrapper(carportEntity,wrapper);
            personalDao.deleteById(personal.getPersonalId());
            MessageEntity messageEntity1 = new MessageEntity();
            messageEntity1.setMessageTitle("您的购车合同签署失败");
            messageEntity1.setMessageContent("您的购车合同编号："+personal.getPersonalContractNum()+",签署失败");
            messageEntity1.setMessageTime(DateUtils.getDateTime());
            messageEntity1.setMessageType("买卖车消息");
            messageEntity1.setMessageIsRead(0);
            messageEntity1.setMessagePostUserId(-1);
            messageEntity1.setMessageGetUserId(personal.getBuyUserId());
            MessageEntity messageEntity2 = new MessageEntity();
            messageEntity2.setMessageTitle("您的购车合同签署失败");
            messageEntity2.setMessageContent("您的购车合同编号："+personal.getPersonalContractNum()+",签署失败");
            messageEntity2.setMessageTime(DateUtils.getDateTime());
            messageEntity2.setMessageType("买卖车消息");
            messageEntity2.setMessageIsRead(0);
            messageEntity2.setMessagePostUserId(-1);
            messageEntity2.setMessageGetUserId(personal.getSellUserId());
            messageDao.insert(messageEntity1);
            messageDao.insert(messageEntity2);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        personalDao.deleteById(personal.getPersonalId());
        return 0;
    }

    @Override
    public IPage<PersonalEntity> listPersonal(int userId, int currentPage, int pageSize) {
        IPage<PersonalEntity> page = new Page<PersonalEntity>(currentPage, pageSize);
        QueryWrapper<PersonalEntity> wrapper = new QueryWrapper<PersonalEntity>();
        wrapper.eq("sellUserId", userId).or().eq("buyUserId", userId);
        try {
            return personalDao.selectPage(page, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IPage<PersonalEntity> listPersonalByCondition(int buyUserId, int sellUserId, int currentPage, int pageSize) {
        IPage<PersonalEntity> page = new Page<PersonalEntity>(currentPage, pageSize);
        QueryWrapper<PersonalEntity> wrapper = new QueryWrapper<PersonalEntity>();
        wrapper.eq("buyUserId", buyUserId).or().eq("sellUserId", sellUserId);
        try {
            return personalDao.selectPage(page, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IPage<PersonalEntity> listAllPersonal(int currentPage, int pageSize) {
        IPage<PersonalEntity> page = new Page<PersonalEntity>(currentPage, pageSize);
        try {
            return personalDao.selectPage(page, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
