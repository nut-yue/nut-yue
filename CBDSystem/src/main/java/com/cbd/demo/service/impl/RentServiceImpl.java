package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.RentBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.dao.ICarportDao;
import com.cbd.demo.dao.IRentDao;
import com.cbd.demo.dao.IUserDao;
import com.cbd.demo.entity.CarportEntity;
import com.cbd.demo.entity.RentEntity;
import com.cbd.demo.service.IRentService;
import com.cbd.demo.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : RentServiceImpl
 * @Date ：2019/5/31 23:21
 * @Desc ：类的介绍：
 * @author：岳超
 */
@Service
public class RentServiceImpl implements IRentService {

    @Autowired
    private IRentDao rentDao;
    @Autowired
    private ICarportDao carportDao;
    @Autowired
    private IUserDao userDao;

    @Override
    public int insertRent(RentBean rent) {
        int result = 0;
        try {
            // 创建一个租借信息实体类对象
            RentEntity rentEntity = new RentEntity();
            // 将租借Bean对象转换为实体类对象
            BeanUtils.copyProperties(rent,rentEntity);
            // 设置实体类中的车位id
            rentEntity.setCarportId(rent.getCarportBean().getCarportId());
            // 根据车位id将车位状态修改为发布招租信息中
            UpdateWrapper<CarportEntity> wrapper = new UpdateWrapper<>();
            wrapper.eq("carportId",rent.getCarportBean().getCarportId())
                   .set("carportStatus","发布出租信息中");
            carportDao.updateByWrapper(null,wrapper);
            // 根据车位id删除其他出租信息
            // 新建条件构造器
            QueryWrapper <RentEntity> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("carportId",rent.getCarportBean().getCarportId());
            rentDao.deleteByCondition(wrapper1);
            // 将实体类对象存入数据库并返回
            result = rentDao.insert(rentEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }

    @Override
    public RentBean getRent(int rentId) {
        RentBean rentBean = new RentBean();
        try {
            // 通过出租信息展示id查看出租详细信息
            RentEntity rentEntity = rentDao.selectById(rentId);
            if(rentEntity!=null) {
                // 通过车位id查询车位信息
                CarportEntity carportEntity = carportDao.getCarport(rentEntity.getCarportId());
                // 创建新的车位实体Bean
                CarportBean carportBean = new CarportBean();
                // 将车位信息实体类转换为车辆实体Bean
                BeanUtils.copyProperties(carportEntity, carportBean);
                // 创建新的用户实体Bean
                UserBean userBean = new UserBean();
                // 根据车位所属人id查找所属人实体类,并转换为Bean
                BeanUtils.copyProperties(userDao.getById(carportEntity.getUserId()), userBean);
                // 设置车位Bean所属人
                carportBean.setUserBean(userBean);
                // 创建新的租借信息展示实体bean
                rentBean = new RentBean();
                // 将实体类转换为bean
                BeanUtils.copyProperties(rentEntity, rentBean);
                // 设置租借信息Bean中的车辆对象
                rentBean.setCarportBean(carportBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rentBean;
    }

    @Override
    public IPage<RentBean> listRent(Integer userId,String carportAddress, int currentPage, int pageSize) {
        // 创建分页对象用于封装最终返回数据
        IPage<RentBean> rentPage = new Page<>();
        try {
            // 创建条件构造器
            QueryWrapper <CarportEntity> carportWrapper = new QueryWrapper<>();
            carportWrapper.like(StringUtils.isNotEmpty(carportAddress),"carportAddress",carportAddress)
                          .eq("carportStatus","发布出租信息中").ne("userId",userId);
            // 创建分页对象
            Page <CarportEntity> carportPage = new Page<>(currentPage,pageSize);
            // 分页查询地址为输入条件的车位信息
            IPage<CarportEntity> carportEntitys = carportDao.listCarport(carportPage, carportWrapper);
            // 创建List集合用于封装最终返回的查询结果
            List <RentBean> rentList = new ArrayList<>();
            // 遍历车位分页对象
            for (CarportEntity record : carportEntitys.getRecords()) {
                // 创建条件构造器，用于根据车位id查询车位出租信息
                QueryWrapper <RentEntity> rentWrapper = new QueryWrapper<>();
                // 得到车位id,并根据车位id查询出租信息
                RentEntity rentEntity = rentDao.selectByCondition(rentWrapper.eq("carportId", record.getCarportId()));
                // 将租借信息实体类和车位信息实体类转换为Bean
                RentBean rentBean = new RentBean();
                CarportBean carportBean = new CarportBean();
                BeanUtils.copyProperties(rentEntity,rentBean);
                BeanUtils.copyProperties(record,carportBean);
                // 设置车位出租信息中的车位bean
                rentBean.setCarportBean(carportBean);
                // 将车位出租信息加入集合
                rentList.add(rentBean);
            }
             // 设置返回的分页对象
            rentPage = PageUtil.transform(carportPage);
            rentPage.setRecords(rentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rentPage;
    }

}
