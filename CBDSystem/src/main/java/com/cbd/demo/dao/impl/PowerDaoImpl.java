package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.dao.IPowerDao;
import com.cbd.demo.entity.PowerEntity;
import com.cbd.demo.mapper.PowerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : PowerDaoImpl
 * @Date ：2019/5/31 16:58
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@Repository
public class PowerDaoImpl implements IPowerDao {
    @Autowired
    private PowerMapper powerMapper;
    @Override
    public List<PowerEntity> listAllPower(int reoleId) throws Exception {
        return powerMapper.listPower(reoleId);
    }
}
