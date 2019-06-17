package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.entity.LogEntity;
import com.cbd.demo.entity.ReservationEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

/**预约持久层测试
 * @author ：杨强
 * @date ：Created in 2019/5/31 0031 14:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationDaoImplTest {
    @Autowired
    private IReservationDao iReservationDao;

    /**
     * 添加方法测试
     */
    @Test
    public void insert() throws Exception{
        ReservationEntity reservation=new ReservationEntity();
        reservation.setReservationType("购买");
        reservation.setReservationDeal("否");
        reservation.setReservationContent("XXXX服务态度太差");
        reservation.setCarportId(10);
        reservation.setAppointedUserId(2);
        reservation.setAppointerUserId(3);

        int i=iReservationDao.insert(reservation);
        //System.out.println(i);
        Assert.assertEquals(i,1);
    }

    /**
     * 测试修改方法
     * @throws Exception
     */
    @Test
    public void update() throws Exception{
      //  ReservationEntity reservation=new ReservationEntity();
        UpdateWrapper<ReservationEntity> wrapper = new UpdateWrapper<>();
        wrapper.set("reservationDeal","是").eq("reservationId",12);
        iReservationDao.update(null,wrapper);

    }

    /**
     * 根据id查看方法测试
     * @throws Exception
     */
    @Test
    public void findById()throws Exception{
        ReservationEntity reservation=iReservationDao.selectById(11);
        //System.out.println(reservation);
        Assert.assertNotNull(reservation);
    }

    /**
     * 分页查询测试
     */
    @Test
    public void IPage() throws Exception{
        QueryWrapper<ReservationEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("appointerUserId",2).like("reservationDeal","是");
        Page<ReservationEntity> page=new Page<>();
        page.setCurrent(1);
        page.setSize(1);
        List<Object> result=Collections.singletonList(iReservationDao.selectPage(page,queryWrapper));
        Assert.assertNotNull(result);

    }
}
