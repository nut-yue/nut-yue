package com.cbd.demo.service.impl;

import com.cbd.demo.entity.PersonalEntity;
import com.cbd.demo.service.IPersonalService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
/**
 * @ClassName : PerformanceServiceImplTest
 * @Date ：2019/6/03 12:40
 * @Desc ：类的介绍：个人买卖合同的业务实现类
 * @author：刘划轩
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalServiceImplTest {
    @Autowired
    private IPersonalService personalService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addPersonal() {
        PersonalEntity personalEntity=new PersonalEntity();
        personalEntity.setPersonalContractNum("111111");
        personalEntity.setPersonalPrice(10000);
        personalEntity.setPersonalDate("2019-06-03");
        personalEntity.setPersonalBuyIsSigning("未签约");
        personalEntity.setPersonalSellIsSigning("未签约");
        personalEntity.setSellUserId(1);
        personalEntity.setBuyUserId(2);
        personalEntity.setCarportId(1);
        Assert.assertNotNull(personalService.addPersonal(personalEntity));
    }

    @Test
    public void getPersonal() {
        Assert.assertNotNull(personalService.getPersonal(1));
    }

    @Test
    public void updateSelfPersonalSigning() {
        PersonalEntity personalEntity=new PersonalEntity();
        personalEntity.setPersonalId(1);
        personalEntity.setPersonalContractNum("111111");
        personalEntity.setPersonalPrice(10000);
        personalEntity.setPersonalDate("2019-06-03");
        personalEntity.setPersonalBuyIsSigning("签约");
        personalEntity.setPersonalSellIsSigning("签约");
        personalEntity.setSellUserId(1);
        personalEntity.setBuyUserId(2);
        personalEntity.setCarportId(1);
        Assert.assertNotNull(personalService.updateSelfPersonalSigning(personalEntity));
    }

    @Test
    public void updatePersonalSigning() {
        PersonalEntity personalEntity=new PersonalEntity();
        personalEntity.setPersonalId(1);
        personalEntity.setPersonalContractNum("111111");
        personalEntity.setPersonalPrice(10000);
        personalEntity.setPersonalDate("2019-06-03");
        personalEntity.setPersonalBuyIsSigning("签约");
        personalEntity.setPersonalSellIsSigning("未签约");
        personalEntity.setSellUserId(1);
        personalEntity.setBuyUserId(2);
        personalEntity.setCarportId(1);
        Assert.assertNotNull(personalService.updatePersonalSigning(personalEntity));
    }

    @Test
    public void listPersonal() {
        Assert.assertNotNull(personalService.listPersonal(1,1,3));
    }

    @Test
    public void listPersonalByCondition() {
        Assert.assertNotNull(personalService.listPersonalByCondition(0,1,1,3));
    }

    @Test
    public void listAllPersonal() {
        Assert.assertNotNull(personalService.listAllPersonal(1,3));
    }
}