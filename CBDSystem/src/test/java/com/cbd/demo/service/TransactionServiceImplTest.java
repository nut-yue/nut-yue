package com.cbd.demo.service;

import com.cbd.demo.bean.TransactionBean;
import com.cbd.demo.bean.UserBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：杨强
 * @date ：Created in 2019/6/2 0002 22:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceImplTest {
    @Autowired
    private ITransactionService iTransactionService;

    @Test
    public void addTest(){
        UserBean rent=new UserBean();
        rent.setUserId(2);
        TransactionBean transactionBean=new TransactionBean();
        transactionBean.setCarportId(24);

        transactionBean.setLeaseUserUserBean(rent);
        transactionBean.setRentUserUserBean(rent);
        transactionBean.setTransactionEndTime("1888-11-11");
        transactionBean.setTransactionTime("1888-01-01");
        transactionBean.setTransactionPrice(22222);
        int i=iTransactionService.saveTransaction(transactionBean);
        Assert.assertEquals(i,1);
    }

    @Test
    public void select(){
        TransactionBean transactionBean=iTransactionService.getTransaction(2);
        System.out.println(transactionBean);
    }

}
