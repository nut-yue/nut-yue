package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.ComplaintBean;
import com.cbd.demo.entity.ComplaintEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : ComplaintServiceImplTest
 * @Date ：2019/6/2 8:52
 * @Desc ：类的介绍：
 * @author：作者：峗权
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintServiceImplTest {
    @Autowired
    private IComplaintService complaintService;

    @Test
    public void findComplaintAllTest(){
        IPage<ComplaintBean> page = complaintService.findComplaintAll(1,1,3,"2019-01-01",null);
        Assert.assertNotNull(page);
    }

    @Test
    public void getComplaintTest(){
        ComplaintBean complaintBean = complaintService.getComplaint(1);
        Assert.assertNotNull(complaintBean);
    }

    @Test
    public void saveComplaintTest(){
        ComplaintBean complaintEntity = new ComplaintBean();
        complaintEntity.setComplaintStatus("审核中");
        complaintEntity.setComplaintTypes("买车投诉");
        complaintEntity.setComplaintDescription("jiushixiangtousu");
        int num = complaintService.saveComplaint(complaintEntity);
        Assert.assertEquals(num,1);
    }

    @Test
    public void updateComplaintTest(){
        ComplaintBean complaintBean = complaintService.getComplaint(12);
        complaintBean.setComplaintStatus("已驳回");
        int num = complaintService.updateComplaint(complaintBean);
        Assert.assertEquals(num,1);
    }
    @Test
    public void getComplaintBeanTest(){
        ComplaintBean complaintBean = complaintService.getComplaintBean(1);
        Assert.assertNotNull(complaintBean);
    }
}
