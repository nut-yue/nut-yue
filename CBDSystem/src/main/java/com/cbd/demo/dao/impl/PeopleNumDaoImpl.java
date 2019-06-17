package com.cbd.demo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cbd.demo.dao.IPeopleNumDao;
import com.cbd.demo.entity.PeopleNumEntity;
import com.cbd.demo.mapper.PeopleNumMapper;
import com.cbd.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：杨强
 * @date ：Created in 2019/5/31 0031 10:52
 */
@Repository
public class PeopleNumDaoImpl implements IPeopleNumDao {

    @Autowired
    private PeopleNumMapper peopleNumMapper;
    @Override
    public int insert(PeopleNumEntity peopleNumEntity)throws Exception {
        return peopleNumMapper.insert(peopleNumEntity);
    }

    @Override
    public List<PeopleNumEntity> selectList(Wrapper<PeopleNumEntity> queryWrapper) throws Exception{
        return peopleNumMapper.selectList(queryWrapper);
    }

    /**
     * 统计前几天的所有时间段的在线人数
     * @param peopleNumDate 日期
     * @return
     */
    @Override
    public List<PeopleNumEntity> sumPeopleNum(String peopleNumDate) {
        return peopleNumMapper.sumPeopleNum(peopleNumDate);
    }
}
