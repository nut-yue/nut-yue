package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.RentBean;
import com.cbd.demo.bean.SaleBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.dao.*;
import com.cbd.demo.entity.*;
import com.cbd.demo.service.ICarportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : CarportServiceImpl
 * @Date ：2019/5/31 14:32
 * @Desc ：类的介绍：
 * @author：岳超
 */
@Service
public class CarportServiceImpl implements ICarportService {

    @Autowired
    ICarportDao carportDao;
    @Autowired
    private IRentDao rentDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private ISaleDao saleDao;
    @Autowired
    private IReservationDao reservationDao;

    @Override
    public int saveCarport(CarportBean carport) {
        int result = 0;
        try {
            // 新建车位实体类对象
            CarportEntity carportEntity = new CarportEntity();
            // 将车位实体bean对象转换为车位实体对象
            BeanUtils.copyProperties(carport,carportEntity);
            // 设置成为所属人id
            carportEntity.setUserId(carport.getUserBean().getUserId());
            // 将车位状态修改为待审核
            carportEntity.setCarportStatus("审核中");
            // 向数据库中添加一条新的个人车位信息
            result = carportDao.inserCarport(carportEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public IPage<CarportEntity> listCarport(int id, int currentPage, int pageSize, String state) {
        IPage<CarportEntity> pages = new Page<>();
        try {
            // 新建条件查询器
            QueryWrapper <CarportEntity> wrapper = new QueryWrapper<CarportEntity>();
            // 新建分页对象,将每页显示条数和当前页数传入
            Page<CarportEntity> page = new Page<CarportEntity>(currentPage,pageSize);
            // 当用户id为0时查询全部车位
            boolean value = true;
            if(id == 0){
                value=false;
            }
            // 查询车位状态为xxx的,属于userid=id的用户的所有车位,前台需要将车位状态写死,不进行输入
            wrapper.eq(value,"userId",id)
                    .eq(StringUtils.isNotEmpty(state),"carportStatus",state)
                    .notLike(value,"carportStatus","已卖出");
            // 返回分页对象
            pages = carportDao.listCarport(page, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pages;
    }


    @Override
    public CarportBean getCarport(int carportId) {
        CarportBean carport = new CarportBean();
        try {
           // 根据车位id得到车位实体类
           CarportEntity carportEntity = carportDao.getCarport(carportId);
           // 根据车位所属人id查询得到用户实体类
            UserEntity user = userDao.getById(carportEntity.getUserId());
            // 创建一个用户实体Bean
            UserBean userBean = new UserBean();
            if(user!=null){
                // 将用户实体类转换为实体Bean
                BeanUtils.copyProperties(user,userBean);
            }

            // 将车位实体类转换为实体Bean
            BeanUtils.copyProperties(carportEntity,carport);
            // 将userBean封装到CarportBean中
            carport.setUserBean(userBean);
            // 根据车位id查询出租信息
            // 新建条件构造器
            QueryWrapper<RentEntity> rentWrapper = new QueryWrapper<>();
            rentWrapper.eq("carportId",carportId);
            // 获得出租信息对象
            RentEntity rentEntity = rentDao.selectByCondition(rentWrapper);
            if(rentEntity!=null){
                // 创建出租信息实体Bean
                RentBean rentBean = new RentBean();
                // 将实体类转换为实体Bean
                BeanUtils.copyProperties(rentEntity,rentBean);
                // 将出租信息设置到Carport中
                carport.setRentBean(rentBean);
            }
            // 根据车位信息查询出售信息
            // 创建条件构造器
            QueryWrapper<SaleEntity> saleWrapper = new QueryWrapper<>();
            saleWrapper.eq("carportId",carportId);
            // 新建分页对象
            Page<SaleEntity> page = new Page<>(1,1);
            IPage<SaleEntity> sales = saleDao.findAllSaleInfo(page, saleWrapper);
            if(sales.getRecords().size()!=0){
                SaleEntity saleEntity = sales.getRecords().get(0);
                // 创建出售信息实体Bean
                SaleBean saleBean = new SaleBean();
                // 将实体类转换为实体Bean
                BeanUtils.copyProperties(saleEntity,saleBean);
                // 将出售信息实体Bean封装到carport中
                carport.setSaleBean(saleBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carport;
    }

    @Override
    public int updateCarportStatus(CarportBean carportBean) {
        int result = 0;
        try {
            // 新建条件构造器
            UpdateWrapper <CarportEntity> wrapper = new UpdateWrapper<>();
            if(("发布出租信息中").equals(carportBean.getCarportStatus())||("发布出售信息中").equals(carportBean.getCarportStatus())) {
                // 根据车位id修改车位状态为下架
                wrapper.eq("carportId", carportBean.getCarportId()).set("carportStatus", "待发布");
                // 修改成功的记录条数
                result = carportDao.updateByWrapper(null, wrapper);
                // 根据车位id删除出租展示信息
                UpdateWrapper<RentEntity> rentWrapper = new UpdateWrapper<>();
                rentWrapper.eq("carportId", carportBean.getCarportId());
                rentDao.deleteByCondition(rentWrapper);
                // 根据车位id删除出售展示信息
                UpdateWrapper<SaleEntity> saleWrapper = new UpdateWrapper<>();
                saleWrapper.eq("carportId", carportBean.getCarportId());
                saleDao.delByCondition(saleWrapper);
                // 修改该车位预定信息为已拒绝
                // 新建条件构造器
                UpdateWrapper<ReservationEntity> reservationWrapper = new UpdateWrapper<>();
                reservationWrapper.eq("carportId", carportBean.getCarportId()).set("reservationDeal", "已拒绝");
                reservationDao.update(null, reservationWrapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateStatus(CarportBean carportBean) {
        int result = 0;
        try {
            if(("审核中").equals(carportBean.getCarportStatus())) {
                // 新建条件构造器
                UpdateWrapper<CarportEntity> wrapper = new UpdateWrapper<>();
                // 按照车位id修改车位状态为待发布
                wrapper.eq("carportId", carportBean.getCarportId()).set("carportStatus", "待发布");
                // 返回修改成功记录条数
                result = carportDao.updateByWrapper(null, wrapper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteCarport(int carportId) {
        int result = 0;
        try {
            // 车位审核未通过，根据车辆id删除车位
            result = carportDao.deleteCarport(carportId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
