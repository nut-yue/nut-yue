package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.ILogDao;
import com.cbd.demo.entity.LogEntity;
import com.cbd.demo.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @ClassName : LogDaoImpl
 * @Date ：2019/5/31 10:47
 * @Desc ：类的介绍：记录持久层接口实现
 * @author：作者：周陆成
 */
@Repository
public class LogDaoImpl implements ILogDao {
    /**
     * 日志记录mapper
     */
    @Autowired
    private LogMapper logMapper;
    @Override
    /**
     * 添加日志记录
     * 返回影响行数
     */
    public int insert(LogEntity logEntity) throws Exception {
        return logMapper.insert(logEntity);
    }

    /**
     * 条件查询所有日志记录信息 并进行分页
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return
     */
    @Override
    public IPage<LogEntity> selectPage(IPage<LogEntity> page, Wrapper<LogEntity> queryWrapper) throws Exception {
        return logMapper.selectPage(page,queryWrapper);
    }
}
