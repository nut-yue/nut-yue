package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.LogEntity;

/**
 * @ClassName : ILogService
 * @Date ：2019/5/28 15:25
 * @Desc ：类的介绍：日志持久化接口
 * @author：作者：胡平
 */
public interface ILogDao {
    /**
     * 添加记录
     * @param logEntity 对象实例
     * @return 操作记录条数
     */
    int insert(LogEntity logEntity)throws Exception;

    /**
     * 按条件分页查询
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return  类型IPage<LogEntity> 分页封装对象
     */
    IPage<LogEntity> selectPage(IPage<LogEntity> page, Wrapper<LogEntity> queryWrapper)throws Exception;
}
