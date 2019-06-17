package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.TenantryEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @ClassName : ITenantryDaoImplTest
 * @Date ：2019/5/31 11:18
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TenantryDaoImplTest {
    @Autowired
    private ITenantryDao tenantrydao;

    @Test
    public void add() throws Exception {
        TenantryEntity tenantryEntity = new TenantryEntity();
        tenantryEntity.setTenantryContractNum("232423");
        tenantryEntity.setTenantryEndTime("2019-10-12");
        int i = tenantrydao.addNewTenantry(tenantryEntity);
        Assert.eq(i, 1, null);
    }
    @Test
    public void updateTenantrydao() throws Exception{
        TenantryEntity tenantryEntity = new TenantryEntity();
        tenantryEntity.setTenantryContractNum("123");
        tenantryEntity.setTenantryEndTime("2019-10-12");
        UpdateWrapper<TenantryEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("tenantryId", 11);
        int i = tenantrydao.updateTenantry(tenantryEntity,wrapper);
        Assert.eq(i, 1, null);
    }

    @Test
    public void listPage() throws Exception {
        IPage<TenantryEntity> page = new Page<>();
        page.setPages(1);
        page.setSize(10);
        IPage<TenantryEntity> tenantrys = tenantrydao.showAllTenantry(page, null);
//        System.out.println(tenantrys);
        Assert.notNull(null, tenantrys);
    }

    @Test
    public void getById() throws Exception {
        TenantryEntity byId = tenantrydao.findById(1);
        System.out.println(byId);
        Assert.notNull(null, byId);
    }
}
