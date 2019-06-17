package com.cbd.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.TransactionEntity;

/**
 * @ClassName : TransactionService
 * @Date ：2019/5/28 15:31
 * @Desc ：类的介绍：出租交易记录Mapper接口
 * @author：作者：王佳伟
 */
public interface TransactionMapper extends SqlMapper, BaseMapper<TransactionEntity> {
}
