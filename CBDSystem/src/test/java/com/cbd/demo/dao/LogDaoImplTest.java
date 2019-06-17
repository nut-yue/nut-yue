package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.LogEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName : LogDaoImplTest
 * @Date ：2019/5/31 12:41
 * @Desc ：类的介绍：测试日志记录持久层接口
 * @author：作者：周陆成
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogDaoImplTest {
    @Autowired
    private ILogDao logDao;

    /**
     * 添加日志测试
     */
    @Test
    public void addLog()throws Exception{
        LogEntity logEntity =new LogEntity();
        logEntity.setLogType("后台操作");
        logEntity.setLogOperator("测试");
        logEntity.setLogIpAddress("测试地址");
        logEntity.setLogDate("2019-5-31");
        logEntity.setAdminId(1);
        logEntity.setLogContent("测试");
        int add=logDao.insert(logEntity);
        Assert.assertNotNull(add);
        //System.out.println(add);
    }

    /**
     * 分页动态模糊测试
     */
    @Test
    public void findLogBypage()throws Exception{
        QueryWrapper <LogEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("AdminId",1)
                .likeRight("Logcontent","测");
        Page<LogEntity>page1=new Page<LogEntity>();
        page1.setCurrent(1);
        page1.setSize(1);
        List<Object> users = Collections.singletonList(logDao.selectPage(page1, wrapper));
        Assert.assertNotNull(users);
    }
}
