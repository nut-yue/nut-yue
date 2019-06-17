package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.ExternalBean;
import com.cbd.demo.entity.ExternalEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : ExternalServiceImplTest
 * @Date ：2019/6/1 20:04
 * @Desc ：类的介绍：ExternalServiceImpl测试类
 * @author：作者：峗权
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExternalServiceImplTest {
    @Autowired
    private IExternalService externalService;

    @Test
    public void contractExternalTest(){
        ExternalBean externalBean = externalService.getExternalByExternalId(6);
        ExternalBean external = new ExternalBean();
        external.setOriginalBean(externalBean);
        external.setExternalNo("wq634234");
        external.setExternalUnit("项目0组");
        external.setExternalContact("皮皮怪经理");
        external.setExternalPhone("18327484733");
        external.setExternalAddress("成都市武侯区科华北路二段9号");
        external.setExternalffectivedate("2019-01-01");
        external.setExternalDeadline("2019-06-15");
        external.setExternalPrice(66666);
        external.setExternalCopy("11.jpg");
        external.setExternalContractStatus("未生效");
        external.setExternaLinkman("公孙策");
        external.setExternaLinkmanTel("15903948576");
        int num = externalService.contractExternal(external);
        Assert.assertEquals(num,1);

    }

    @Test
    public void getExternalByExternalIdTest(){
        ExternalBean externalBean = externalService.getExternalByExternalId(1);
        Assert.assertNotNull(externalBean);
    }

    @Test
    public void listExternalTest(){
        IPage<ExternalEntity> pages = externalService.listExternal(1,5,"2019-02-02","2019-03-01","已过期");
        Assert.assertNotNull(pages);
    }

    @Test
    public void cancelExternalTest(){
        ExternalBean externalBean = externalService.getExternalByExternalId(1);
        int num = externalService.contractExternal(externalBean);
        Assert.assertEquals(num,1);
    }

    @Test
    public void saveExternalTest(){
        ExternalBean external = new ExternalBean();
        external.setExternalNo("wq634234");
        external.setExternalUnit("项目十二组");
        external.setExternalContact("皮皮guai经理");
        external.setExternalPhone("66666666666");
        external.setExternalAddress("成都市武侯区科华北路二段11号");
        external.setExternalffectivedate("2019-01-01");
        external.setExternalDeadline("2019-06-15");
        external.setExternalPrice(777777);
        external.setExternalCopy("12.jpg");
        external.setExternaLinkman("公孙策");
        external.setExternaLinkmanTel("15903948576");

        int num = externalService.saveExternal(external,null);
        Assert.assertNotEquals(num,0);

        //externalService.saveExternal(external,);
    }
}
