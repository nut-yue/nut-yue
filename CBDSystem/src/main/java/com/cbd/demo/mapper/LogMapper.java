package com.cbd.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.CompanyEntity;
import com.cbd.demo.entity.LogEntity;

/**
 * @ClassName : ILogService
 * @Date ：2019/5/28 15:25
 * @Desc ：类的介绍：日志Mapper接口
 * @author：作者：王佳伟
 */
public interface LogMapper extends SqlMapper, BaseMapper<LogEntity> {
}
