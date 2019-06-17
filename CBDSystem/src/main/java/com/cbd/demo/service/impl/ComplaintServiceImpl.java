package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.bean.BillBean;
import com.cbd.demo.bean.ComplaintBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.dao.IAdminDao;
import com.cbd.demo.dao.IBillDao;
import com.cbd.demo.dao.IComplaintDao;
import com.cbd.demo.dao.IUserDao;
import com.cbd.demo.entity.AdminEntity;
import com.cbd.demo.entity.BillEntity;
import com.cbd.demo.entity.ComplaintEntity;
import com.cbd.demo.entity.UserEntity;
import com.cbd.demo.service.IComplaintService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : ComplaintServiceImpl
 * @Date ：2019/6/1 10:10
 * @Desc ：类的介绍：投诉业务接口实现类
 * @author：作者：峗权
 */
@Service
public class ComplaintServiceImpl implements IComplaintService {
    /**注入complaint的dao*/
    @Autowired
    private IComplaintDao complaintDao;
    /**注入user的dao*/
    @Autowired
    private IUserDao userDao;
    /**注入bill的dao*/
    @Autowired
    private IBillDao billDao;
    /**注入admin的dao*/
    @Autowired
    private IAdminDao adminDao;

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
    @Override
    public IPage<ComplaintBean> findComplaintAll(int id, int currentPage, int pageSize, String oldDate, String newDate) {
        // 1 健壮性判断，并且给一些值付默认值
        if (currentPage < 0) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        Page<ComplaintEntity> page = new Page<>(currentPage,pageSize);
        IPage<ComplaintBean> pageBean = new Page<ComplaintBean>();
        List<ComplaintBean> list = new ArrayList<ComplaintBean>();

        // 2 动态拼接条件
        QueryWrapper<ComplaintEntity> queryWrapper = new QueryWrapper<>();
        if(id==-1){
            queryWrapper.eq("complaintStatus","审核中")
                    .ge(StringUtils.isNotEmpty(oldDate),"complaintDate",oldDate)
                    .le(StringUtils.isNotEmpty(newDate),"complaintDate",newDate);
        }else {
            queryWrapper.eq("userId", id)
                    .ge(StringUtils.isNotEmpty(oldDate), "complaintDate", oldDate)
                    .le(StringUtils.isNotEmpty(newDate), "complaintDate", newDate);
        }
        try {
            IPage<ComplaintEntity> iPage = complaintDao.selectPage(page,queryWrapper);
            for (ComplaintEntity complaintEntity : iPage.getRecords()) {
                ComplaintBean complaintBean = new ComplaintBean();
                BeanUtils.copyProperties(complaintEntity,complaintBean);

                UserEntity userEntity1 = userDao.getById(complaintEntity.getUserId());
                UserEntity userEntity2 = userDao.getById(complaintEntity.getRespondentUserId());
                UserBean userBean1 = new UserBean();
                UserBean userBean2 = new UserBean();

                BeanUtils.copyProperties(userEntity1,userBean1);
                BeanUtils.copyProperties(userEntity2,userBean2);

                complaintBean.setUserBean(userBean1);
                complaintBean.setRespondentUserBean(userBean2);

                list.add(complaintBean);
            }
            pageBean.setTotal(iPage.getTotal());
            pageBean.setPages(iPage.getPages());
            pageBean.setCurrent(iPage.getPages());
            pageBean.setSize(iPage.getSize());


            pageBean.setRecords(list);
            return pageBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 查看单个投诉记录，同时根据投诉双方id查看双方的姓名
     * @param id 投诉记录id
     * @return  ComplaintBean类型的投诉记录对象
     */
    @Override
    public ComplaintBean getComplaint(int id) {
        try {
            //根据投诉ID查看投诉记录
            ComplaintEntity complaintEntity = complaintDao.getComplaint(id);
            //根据投诉双方id获取用户entity(userEntity1为投诉人，userEntity2为被投诉对象)
            UserEntity userEntity1 = userDao.getById(complaintEntity.getUserId());
            UserEntity userEntity2 = userDao.getById(complaintEntity.getRespondentUserId());
            UserBean userBean1 = new UserBean();
            UserBean userBean2 = new UserBean();

            QueryWrapper<AdminEntity> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("adminId",userEntity1.getAdminId());
            QueryWrapper<AdminEntity> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("adminId",userEntity2.getAdminId());
            AdminEntity adminEntity1 = adminDao.selectOne(queryWrapper1);
            AdminEntity adminEntity2 = adminDao.selectOne(queryWrapper2);

            AdminBean adminBean1 = new AdminBean();
            AdminBean adminBean2 = new AdminBean();
            if(adminEntity1!=null){
                BeanUtils.copyProperties(adminEntity1,adminBean1);
                userBean1.setAdminBean(adminBean1);
            }
            if(adminEntity2!=null){
                BeanUtils.copyProperties(adminEntity2,adminBean2);
                userBean2.setAdminBean(adminBean2);
            }

            QueryWrapper<BillEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("billId",complaintEntity.getBillId());
            BillEntity billEntity = billDao.selectOne(wrapper);
            BillBean billBean = new BillBean();
            //将Entity转换为bean
            if(userEntity1!=null){
                BeanUtils.copyProperties(userEntity1,userBean1);
            }
            if(userEntity2!=null){
                BeanUtils.copyProperties(userEntity2,userBean2);
            }
            if(billEntity!=null){
                BeanUtils.copyProperties(billEntity,billBean);
            }

            ComplaintBean complaintBean = new ComplaintBean();
            BeanUtils.copyProperties(complaintEntity,complaintBean);
            if(userBean1!=null){
                complaintBean.setUserBean(userBean1);
            }
            if(userBean2!=null){
                complaintBean.setRespondentUserBean(userBean2);
            }
            if(billBean!=null){
                complaintBean.setBillBean(billBean);
            }
            return complaintBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加投诉，将投诉状态设置为“审核中”，再添加
     * @param complaint 投诉对象
     * @return 是否成功，失败返回0，成功返回1
     */
    @Override
    public int saveComplaint(ComplaintBean complaint) {
        //健壮性
        if(complaint==null){
            return 0;
        }
        //将bean转换为entity
        ComplaintEntity complaintEntity = new ComplaintEntity();
        BeanUtils.copyProperties(complaint,complaintEntity);
        complaintEntity.setComplaintStatus("审核中");
        //设置特有属性
        UserBean userBean1 = complaint.getUserBean();
        UserBean userBean2 = complaint.getRespondentUserBean();
        BillBean billBean = complaint.getBillBean();
        if(userBean1!=null){
            complaintEntity.setUserId(userBean1.getUserId());
        }
        if(userBean2!=null){
            complaintEntity.setRespondentUserId(userBean2.getUserId());
        }
        if(billBean!=null){
            complaintEntity.setBillId(billBean.getBillId());
        }

        try {
           return complaintDao.insertComplaint(complaintEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 管理员修改投诉信息
     * @param complaint 投诉信息对象
     * @return 是否成功，失败返回0，成功返回1
     */
    @Override
    public int updateComplaint(ComplaintBean complaint) {
        //健壮性
        if(complaint==null){
            return 0;
        }
        //将bean转换为entity
        ComplaintEntity complaintEntity = new ComplaintEntity();
        BeanUtils.copyProperties(complaint,complaintEntity);

        UpdateWrapper<ComplaintEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("complaintId",complaint.getComplaintId())
                .set("complaintStatus",complaint.getComplaintStatus());
        try {
            return complaintDao.updateComplaint(complaintEntity,updateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public ComplaintBean getComplaintBean(int billId) {
        QueryWrapper<ComplaintEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("billId",billId);
        try {
            ComplaintEntity complaintEntity = complaintDao.getComplaintEntity(wrapper);
            ComplaintBean complaintBean = new ComplaintBean();
            if(complaintEntity!=null){
                BeanUtils.copyProperties(complaintEntity,complaintBean);
                UserEntity userEntity1 = userDao.getById(complaintEntity.getUserId());
                UserEntity userEntity2 = userDao.getById(complaintEntity.getRespondentUserId());
                UserBean userBean1 = new UserBean();
                UserBean userBean2 = new UserBean();

                QueryWrapper<BillEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("billId",complaintEntity.getBillId());
                BillEntity billEntity = billDao.selectOne(queryWrapper);
                BillBean billBean = new BillBean();
                //将Entity转换为bean
                if(userEntity1!=null){
                    BeanUtils.copyProperties(userEntity1,userBean1);
                }
                if(userEntity2!=null){
                    BeanUtils.copyProperties(userEntity2,userBean2);
                }
                if(billEntity!=null){
                    BeanUtils.copyProperties(billEntity,billBean);
                }
                if(userBean1!=null){
                    complaintBean.setUserBean(userBean1);
                }
                if(userBean2!=null){
                    complaintBean.setRespondentUserBean(userBean2);
                }
                if(billBean!=null){
                    complaintBean.setBillBean(billBean);
                }
                return complaintBean;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
