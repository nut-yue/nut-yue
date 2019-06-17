package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.bean.LogBean;
import com.cbd.demo.dao.ILogDao;
import com.cbd.demo.entity.LogEntity;
import com.cbd.demo.service.ILogService;
import com.cbd.demo.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @ClassName : LogServiceImpl
 * @Date ：2019/6/1 15:32
 * @Desc ：类的介绍： 日志的业务逻辑层的实现类
 * @author：作者：胡平
 */
@Service
@Transactional
public class LogServiceImpl implements ILogService{

    @Autowired
    private ILogDao logDao;

    /**
     * 添加日志
     * @param logBean 日志的实体bean
     * @return
     */
    @Override
    public int saveLog(LogBean logBean) {
        // 1 健壮性判断
        if (StringUtils.isEmpty(logBean) ||
                StringUtils.isEmpty(logBean.getAdminBean())) {
            return 0;
        }
        // 2 Bean转entity
        LogEntity logEntity = new LogEntity();
        BeanUtils.copyProperties(logBean, logEntity);

        // 3 特殊字段的处理
        logEntity.setAdminId(logBean.getAdminBean().getAdminId());
        logEntity.setLogDate(DateUtils.getDateTime());

        // 4 调用dao进行持久化存储
        try {
            return logDao.insert(logEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param logType 日志类型：前台登录、前台操作、后台登录、后台操作 (固定的）
     * @param logOperator 操作人、判断是否为null、null不添加该查询条件、不为null的情况、进行模糊查询
     * @param logContent 操作内容 与上同理（动态条件拼接）
     * @param currentPage 当前页数
     * @param pageSize 每页条数
     * @return IPage对象  分页对象
     */
    @Override
    public IPage<LogEntity> listLog(String logType, String logOperator,
                                  String logContent, int currentPage, int pageSize) {
        // 1 健壮性判断，并且给一些值付默认值
        if (currentPage < 0) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }

        // 2 创建条件查询
        QueryWrapper wrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(logType)) {
            wrapper.eq("logType", logType);
        }
        if (!StringUtils.isEmpty(logOperator)) {
            wrapper.like("logOperator", logOperator);
        }
        if (!StringUtils.isEmpty(logContent)) {
            wrapper.like("logContent", logContent);
        }

        // 3 创建分页对象，添加分页的条件
        IPage<LogEntity> iPage = new Page<LogEntity>(currentPage, pageSize);
        try {
            return logDao.selectPage(iPage, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
