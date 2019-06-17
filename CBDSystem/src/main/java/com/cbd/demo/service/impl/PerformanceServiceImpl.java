package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.PerformanceBean;
import com.cbd.demo.dao.IPerformanceDao;
import com.cbd.demo.entity.PeopleNumEntity;
import com.cbd.demo.entity.PerformanceEntity;
import com.cbd.demo.service.IPerformanceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName : PerformanceServiceImpl
 * @Date ：2019/5/31/0031 22:57
 * @Desc ：类的介绍：性能统计业务实现类
 * @author：张皓
 */
@Service
public class PerformanceServiceImpl implements IPerformanceService {
    @Autowired
    private IPerformanceDao performanceDao;
    @Override
    public IPage<PerformanceEntity> findAllPerformance(String performanceType, String gtPerformanceDate,String ltPerformanceDate, int currentPage, int pageSize) {
        //创建一个分页对象
        IPage<PerformanceEntity> page = new Page<>(currentPage,pageSize);
        //创建一个条件构造器
        QueryWrapper<PerformanceEntity> queryWrapper = new QueryWrapper<>();
        //动态查询类型等于performanceType，响应时间大于gtPerformanceTime，响应时间小于ltPerformanceTime的对象集合
        queryWrapper.eq(StringUtils.isNotEmpty(performanceType),"performanceType",performanceType)
                .gt(StringUtils.isNotEmpty(gtPerformanceDate),"performanceDate",gtPerformanceDate)
                .lt(StringUtils.isNotEmpty(ltPerformanceDate),"performanceDate",ltPerformanceDate);
        IPage<PerformanceEntity> allPerformance = null;
        try {
            //得到对象集合
            allPerformance = performanceDao.findAllPerformance(page, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPerformance;
    }

    @Override
    public PerformanceBean getPerformance(int performanceId) {
        PerformanceBean performanceBean = new PerformanceBean();
        PerformanceEntity performanceEntity = null;
        try {
            //根据性能信息ID得到性能实体对象
            performanceEntity = performanceDao.findByPerformanceId(performanceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将性能实体对象转换为实体Bean
        BeanUtils.copyProperties(performanceEntity,performanceBean);
        return performanceBean;
    }

    @Override
    public int addPerformance(PerformanceEntity performanceEntity) {
        int i = 0;
        try {
            //新增性能信息对象
            i = performanceDao.addPerformance(performanceEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int delPerformance(int performanceId) {
        int i=0;
        try {
            //根据ID删除性能信息对象
            i=performanceDao.delPerformance(performanceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public Map<String, List<Integer>> sumPerformance() {
        Map<String,List<Integer>> map = new HashMap<>();

        QueryWrapper<PerformanceEntity> queryWrapper = new QueryWrapper<>();
        List list = new ArrayList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date w = c.getTime();
        String week = format.format(w);

        List<PerformanceEntity> performanceEntities = performanceDao.listPerformance(week);
        map.put("week",listToObject(performanceEntities));
        map.put("weekDate",listGetDate(performanceEntities));
        return map;
    }
    /**
     * 获取集合对象中getPerformanceTime的值
     * @return
     */
    private List<Integer> listToObject(List<PerformanceEntity> performanceEntities) {
        List<Integer> ints = new ArrayList<>();
        for (PerformanceEntity per : performanceEntities) {
            ints.add(per.getPerformanceTime());
        }
        return ints;
    }

    private List listGetDate(List<PerformanceEntity> performanceEntities){
        List<String> ints = new ArrayList<>();
        for (PerformanceEntity per : performanceEntities) {
            ints.add(per.getPerformanceDate());
        }
        return ints;
    }
}
