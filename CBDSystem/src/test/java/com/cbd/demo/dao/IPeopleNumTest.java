package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cbd.demo.entity.PeopleNumEntity;
import com.cbd.demo.entity.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author ：杨强
 * @date ：Created in 2019/5/31 0031 11:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IPeopleNumTest {
    @Autowired
    private IPeopleNumDao iPeopleNumDao;

    @Test
    public void insert() {
        PeopleNumEntity peopleNumEntity = new PeopleNumEntity();
        peopleNumEntity.setPeopleNums(10);
        peopleNumEntity.setPeopleNumDate("2019-11-12");
        peopleNumEntity.setPeopleNumTime("11:11:11");
        try {
            iPeopleNumDao.insert(peopleNumEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getList() {
        QueryWrapper<PeopleNumEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("peopleNumDate", "2019-05-31");
        try {
            List<PeopleNumEntity> list = iPeopleNumDao.selectList(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
