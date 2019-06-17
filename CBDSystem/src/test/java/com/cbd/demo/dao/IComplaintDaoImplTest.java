package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.ComplaintEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @InterfaceName : IComplaintDaoImplTest
 * @Date ：2019/5/31 14:24
 * @Desc ：测试dao
 * @author：陈云强
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IComplaintDaoImplTest {
    @Autowired
    private IComplaintDao iComplaintDao;

    @Test
    public void selectPage(){
        Page<ComplaintEntity> page=new Page <>(1,2);
        QueryWrapper<ComplaintEntity> wrapper=new QueryWrapper<>();
                wrapper.eq("complaintStatus","未处理");
        try {
            IPage<ComplaintEntity> complaintEntityIPage = iComplaintDao.selectPage(page, wrapper);
            Assert.assertNotNull(complaintEntityIPage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getComplaint(){
        Page<ComplaintEntity> page= new Page<>(1,2);
        QueryWrapper<ComplaintEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("userId",1);
        wrapper.eq("respondentUserId",1);
        try {
            ComplaintEntity complaint = iComplaintDao.getComplaint(1);
            Assert.assertNotNull(complaint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertComplaint(){
        ComplaintEntity complaint = new ComplaintEntity();
        complaint.setComplaintStatus("已驳回");
        complaint.setComplaintDescription("皮皮虾");
        complaint.setComplaintTypes("租车投诉");
        complaint.setComplaintId(12);
        complaint.setComplaintDate("2019-10-10");
        complaint.setRespondentUserId(12);
        try {
            int value = iComplaintDao.insertComplaint(complaint);
            Assert.assertNotEquals(0,value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateComplaint(){
        ComplaintEntity complaint = null;
        try {
            complaint = iComplaintDao.getComplaint(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        complaint.setComplaintStatus("已处理");
        UpdateWrapper<ComplaintEntity> update = new UpdateWrapper<>();
        update.eq("complaintStatus","已驳回").eq("complaintId",2);
        try {
            int result= iComplaintDao.updateComplaint(complaint,update);
            Assert.assertNotEquals(0,result);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
