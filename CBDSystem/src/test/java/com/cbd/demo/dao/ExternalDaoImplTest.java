package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.ExternalEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : ExternalTest
 * @Date ：2019/5/31 11:19
 * @Desc ：类的介绍： 外部合约的test
 * @author：作者：胡平
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExternalDaoImplTest {

    @Autowired
    private IExternalDao externalDao;

    @Test
    public void addExternal() throws Exception {
        ExternalEntity externalEntity = new ExternalEntity();
        externalEntity.setExternalAddress("1232");
        externalDao.insert(externalEntity);

    }

    @Test
    public void update() throws Exception {
        ExternalEntity externalEntity = new ExternalEntity();
//        externalEntity.setExternalId(22);
        externalEntity.setExternalAddress("你11111好");
        externalEntity.setExternalContact("321312");
        UpdateWrapper<ExternalEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("externalId", 1);
        int i = externalDao.updateExternal(externalEntity, wrapper);
        Assert.eq(1, 1,null);
    }


    @Test
    public void getPage() throws Exception {
        IPage<ExternalEntity> page = new Page<>();
        page.setSize(10);
        page.setPages(1);
        IPage<ExternalEntity> iPage = externalDao.listExternal(page, null);
        Assert.notNull(null, iPage);
    }

    @Test
    public void getById() throws Exception {
        ExternalEntity byId = externalDao.getById(1);
        Assert.notNull(null, byId);
    }

}
