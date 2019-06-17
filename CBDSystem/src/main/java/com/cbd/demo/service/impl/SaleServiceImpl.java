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
import com.cbd.demo.dao.ICarportDao;
import com.cbd.demo.dao.ISaleDao;
import com.cbd.demo.dao.IUserDao;
import com.cbd.demo.entity.CarportEntity;
import com.cbd.demo.entity.RentEntity;
import com.cbd.demo.entity.SaleEntity;
import com.cbd.demo.entity.UserEntity;
import com.cbd.demo.service.ISaleService;
import com.cbd.demo.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : SaleServiceImpl
 * @Date ：2019/6/2 14:47
 * @Desc ：类的介绍：出售信息业务实现类
 * @author：作者：陈云强
 */
@Service
public class SaleServiceImpl implements ISaleService {
    @Autowired
    public ISaleDao iSaleDao;
    @Autowired
    public ICarportDao iCarportDao;
    @Autowired
    public IUserDao iUserDao;

    @Override
    public IPage<SaleBean> findAllSaleInfo(String carportAddress, int currentPage, int pageSize) {
        IPage<SaleBean> salePage = new Page<>();
        try {
            //生产条件构造器
            QueryWrapper<CarportEntity> queryWrapper = new QueryWrapper<>();
            //设置条件构造器条件为动态查询车位地址
            queryWrapper.like(StringUtils.isNotEmpty(carportAddress),"carportAddress",carportAddress)
                    .eq("carportStatus","发布出售信息中");
            // 创建分页对象
            Page <CarportEntity> carportPage = new Page<>(currentPage,pageSize);
            // 分页查询地址为输入条件的车位信息
            IPage<CarportEntity> carportEntitys = iCarportDao.listCarport(carportPage, queryWrapper);

            // 创建List集合用于封装最终返回的查询结果
            List <SaleBean> saleList = new ArrayList<>();
            //遍历符合条件的车位
            for (CarportEntity carport : carportEntitys.getRecords()) {
                // 创建条件构造器，用于根据车位id查询车位出售信息
                QueryWrapper <SaleEntity> rentWrapper = new QueryWrapper<>();
                // 得到车位id,并根据车位id查询出售信息
                SaleEntity saleEntity = iSaleDao.selectSale(rentWrapper.eq("carportId", carport.getCarportId()));
                // 将出售信息实体类和车位信息实体类转换为Bean
                SaleBean saleBean = new SaleBean();
                CarportBean carportBean = new CarportBean();
                BeanUtils.copyProperties(saleEntity,saleBean);
                BeanUtils.copyProperties(carport,carportBean);
                //将车辆信息添加出售信息对象中
                saleBean.setCarportBean(carportBean);
                saleList.add(saleBean);
            }
            salePage = PageUtil.transform(carportPage);
            salePage.setRecords(saleList);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return salePage;
    }
    /**
     * 1、根据出售信息id调用出售车位信息dao层具体的出售信息，取出每个车位id在车位表查车位信息
     * 2、得到entity取出数据封装到bean里
     * @param saleId 出售信息ID
     * @return 信息实体bean
     */
    @Override
    public SaleBean getSale(int saleId) {
        SaleBean saleBean = new  SaleBean();
        try {
            //根据出售Id调出售信息
            SaleEntity saleEntity =iSaleDao.findBySaleId(saleId);
            if (saleEntity!=null){
                //通过车位Id查询车位信息
                CarportEntity carportEntity = iCarportDao.getCarport(saleEntity.getCarportId());
                //创建新的车位实体Bean
                CarportBean carportBean= new CarportBean();
                //得到车位实体类封装到实体Bean中
                BeanUtils.copyProperties(carportEntity,carportBean);
                //创建新的用户实体Bean
                UserBean userBean=new UserBean();
                //通过用户Id找到实体类，封装到用户实体Bean
                BeanUtils.copyProperties(iUserDao.getById(carportEntity.getUserId()),userBean);
                //通过车位信息找到用户并放在用户Bean中
                carportBean.setUserBean(userBean);
                //将出售信息实体类封装到实体Bean中
                BeanUtils.copyProperties(saleEntity,saleBean);
                //通过出售信息，找到车位并放在车位Bean中
                saleBean.setCarportBean(carportBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saleBean;
    }
    /**
     * 先删除该车位的其他出售信息，增加出售信息,同时修改车位表车位状态为“发布出售信息中”
     * @param saleBean 出售信息实体类
     * @return 成功1，失败0
     */
    @Override
    public int addSale(SaleBean saleBean) {
        int add=0;
        try {
            if (saleBean.getCarportBean()!=null) {
                // 删除该车位所有出售信息
                iSaleDao.delSale(saleBean.getCarportBean().getCarportId());
                SaleEntity saleEntity = new SaleEntity();
                BeanUtils.copyProperties(saleBean, saleEntity);
                //设置车位Id
                saleEntity.setCarportId(saleBean.getCarportBean().getCarportId());
                // 将车位状态修改为待审核
                UpdateWrapper<CarportEntity> updateWrapper = new UpdateWrapper();
                //根据车位Id，找到车位状态，并修改状态为“发布出售信息中”
                updateWrapper.eq("carportId", saleBean.getCarportBean().getCarportId()).set("carportStatus", "发布出售信息中");
                //修改
                iCarportDao.updateByWrapper(null,updateWrapper);
                //添加车位信息
                add=iSaleDao.addSale(saleEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return add;
    }

    @Override
    public IPage<SaleBean> findAllSaleInfos(Integer userId, String carportAddress, int currentPage, int pageSize) {
        IPage<SaleBean> salePage = new Page<>();
        try {
            //生产条件构造器
            QueryWrapper<CarportEntity> queryWrapper = new QueryWrapper<>();
            //设置条件构造器条件为动态查询车位地址
            queryWrapper.like(StringUtils.isNotEmpty(carportAddress),"carportAddress",carportAddress)
                    .eq("carportStatus","发布出售信息中").ne("userId",userId);
            // 创建分页对象
            Page <CarportEntity> carportPage = new Page<>(currentPage,pageSize);
            // 分页查询地址为输入条件的车位信息
            IPage<CarportEntity> carportEntitys = iCarportDao.listCarport(carportPage, queryWrapper);

            // 创建List集合用于封装最终返回的查询结果
            List <SaleBean> saleList = new ArrayList<>();
            //遍历符合条件的车位
            for (CarportEntity carport : carportEntitys.getRecords()) {
                // 创建条件构造器，用于根据车位id查询车位出售信息
                QueryWrapper <SaleEntity> rentWrapper = new QueryWrapper<>();
                // 得到车位id,并根据车位id查询出售信息
                SaleEntity saleEntity = iSaleDao.selectSale(rentWrapper.eq("carportId", carport.getCarportId()));
                // 将出售信息实体类和车位信息实体类转换为Bean
                SaleBean saleBean = new SaleBean();
                CarportBean carportBean = new CarportBean();
                BeanUtils.copyProperties(saleEntity,saleBean);
                BeanUtils.copyProperties(carport,carportBean);
                //将车辆信息添加出售信息对象中
                saleBean.setCarportBean(carportBean);
                saleList.add(saleBean);
            }
            salePage = PageUtil.transform(carportPage);
            salePage.setRecords(saleList);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return salePage;
    }
}
