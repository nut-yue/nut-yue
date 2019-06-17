package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.Assert;
import com.cbd.demo.bean.ExternalBean;
import com.cbd.demo.bean.ParkspaceBean;
import com.cbd.demo.entity.ParkspaceEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : ParkspaceServiceImplTest
 * @Date ：2019/6/1 17:32
 * @Desc ：类的介绍： CDB车位业务层测试
 * @author：作者：胡平
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkspaceServiceImplTest {

    @Autowired
    private IParkspaceService parkspaceService;

    @Test
    public void getParkspace(){
        ParkspaceBean parkspace = parkspaceService.getParkspace(1);
        Assert.notNull(null,parkspace);
    }
    //批量添加
    @Test
    public void addList() {
        List<ParkspaceBean> list = new ArrayList<>();
        for (int i =1 ; i <= 10; i++) {
            ParkspaceBean parkspaceBean = new ParkspaceBean();
            ExternalBean externalBean = new ExternalBean();
            externalBean.setExternalId(i);
            parkspaceBean.setExternalBean(externalBean);
            list.add(parkspaceBean);
        }
        int i = parkspaceService.saveParkspace(list);
        Assert.eq(i, list.size(), null);
    }

    //批量修改 只能修改状态
    @Test
    public void updateList() {
        List<Integer> list = new ArrayList<>();
        list.add(11);
        list.add(12);
        list.add(13);
        list.add(14);
        int i = parkspaceService.updateParkspace(list, "已租领");
        Assert.eq(i, list.size(), null);
    }


    //分页查询
    @Test
    public void listParkspace () {
        IPage<ParkspaceEntity> pages = parkspaceService.listParkspace(1, 10,
                "空闲", "东");
        Assert.notNull(null, pages);
    }

    //通过租户合约，修改车辆状态
    @Test
    public void updateTena () {
        int i = parkspaceService.updateByTenantryId(1, "已租赁");
        System.out.println(i);
    }

    //通过租户合同查询
    @Test
    public void listByTen() {
        IPage<ParkspaceEntity> parkspaceEntityIPage = parkspaceService.listTenantryId(1, 1, 10);
        Assert.notNull(null, parkspaceEntityIPage);
    }
}
