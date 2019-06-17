package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.IBillDao;
import com.cbd.demo.entity.BillEntity;
import com.cbd.demo.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName : BillDaoImpl
 * @Date ：2019/5/31 10:37
 * @Desc ：接口的介绍： 计费账单持久接口实现类
 * @author：杨强
 */
@Repository
public class BillDaoImpl implements IBillDao {
    @Autowired
    private BillMapper billMapper;

    @Override
    public IPage<BillEntity> selectPage(IPage<BillEntity> page, Wrapper<BillEntity> queryWrapper) throws Exception {
        return billMapper.selectPage(page,queryWrapper);
    }

    @Override
    public List<BillEntity> selectList(Wrapper<BillEntity> queryWrapper) throws Exception {
        return billMapper.selectList(queryWrapper);
    }

    @Override
    public BillEntity selectOne(Wrapper<BillEntity> queryWrapper) throws Exception {
        return billMapper.selectOne(queryWrapper);
    }

    @Override
    public int insert(BillEntity bill) throws Exception {
        return billMapper.insert(bill);
    }
}
