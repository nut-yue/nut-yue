package com.cbd.demo.dao;

import com.cbd.demo.entity.TransactionEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @ClassName : TransactionDaoImplTest
 * @Date ：2019/5/31 10:47
 * @Desc ：类的介绍：
 * @author：作者：王佳伟
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionDaoTest {
    @Autowired
    private ITransactionDao transactionDao;
    @Test
    public void saveTransaction() {
        TransactionEntity transaction  = new TransactionEntity();
        transaction.setTransactionTime("1992-01-01");
        transaction.setTransactionEndTime("2010-01-01");
        transaction.setTransactionPrice(2000);
        transaction.setCarportId(1);
        transaction.setBillId(1);
        transaction.setRentUserId(1);
        transaction.setLeaseUserId(1);
        try {
           int in= transactionDao.saveTransaction(transaction);
            Assert.assertEquals(in,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTransaction() {
        try {
            TransactionEntity tr= transactionDao.getTransaction(1);
           Assert.assertNotNull(tr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
