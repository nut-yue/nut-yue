package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.BillBean;
import com.cbd.demo.bean.DealCountBean;
import com.cbd.demo.entity.BillEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：杨强
 * @date ：Created in 2019/6/2 0002 23:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BillServiceImplTest {
    @Autowired
    private IBillService iBillService;

    /**
     * 测试计算账单功能
     */
    @Test
    public void getMoney(){
        DealCountBean dealCountBean=iBillService.getMoney(1);
        Assert.assertNotNull(dealCountBean);
        //System.out.println(dealCountBean);
    }

    /**
     * 测试获取Bill功能
     */
    @Test
    public void getBill(){
        BillBean billBean=iBillService.getBill(2);
      //  System.out.println(billBean);
        Assert.assertNotNull(billBean);
    }

    /**
     * 测试分页
     */
    @Test
    public void getPage(){
        IPage<BillEntity> page=iBillService.getBillAll(1,1,3,null,null,null);
        Assert.assertNotNull(page);
    }
}
