package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cbd.demo.bean.PeopleNumBean;
import com.cbd.demo.dao.IPeopleNumDao;
import com.cbd.demo.entity.PeopleNumEntity;
import com.cbd.demo.service.IPeopleNumService;
import com.cbd.demo.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

//            INSERT INTO `t_peoplenum` VALUES (null, '2019-06-3', '00:00:00', 121);
//            INSERT INTO `t_peoplenum` VALUES (null, '2019-06-3', '03:00:00', 329);
//            INSERT INTO `t_peoplenum` VALUES (null, '2019-06-3', '06:00:00', 525);
//            INSERT INTO `t_peoplenum` VALUES (null, '2019-06-3', '09:00:00', 720);
//            INSERT INTO `t_peoplenum` VALUES (null, '2019-06-3', '12:00:00', 824);
//            INSERT INTO `t_peoplenum` VALUES (null, '2019-06-3', '15:00:00', 923);
//            INSERT INTO `t_peoplenum` VALUES (null, '2019-06-3', '18:00:00', 226);
//            INSERT INTO `t_peoplenum` VALUES (null, '2019-06-3', '21:00:00', 622);
/**
 * @ClassName : PeopleNumServiceImpl
 * @Date ：2019/5/31 14:57
 * @Desc ：类的介绍： 在线统计人数的实现类
 * @author：作者：胡平
 */
@Service
@Transactional
public class PeopleNumServiceImpl implements IPeopleNumService{

    @Autowired
    private IPeopleNumDao peopleNumDao;
    /**一天、一周、一月的毫秒值*/
    private final long DAY = 24 * 60 * 60 * 1000;
    private final long WEEK = 24 * 60 * 60 * 1000 * 7;

    /**
     * 添加在线人数
     * peopleNumDate 和 peopleNumTime两个字段是在service进行封装
     * @param peopleNumBean 在线人数统计的实体bean
     * @return int 类型 成功返回影响函数、失败返回0
     */
    @Override
    public int savePeopleNum(PeopleNumBean peopleNumBean) {
        //1 判断对象是否存在
        if (StringUtils.isEmpty(peopleNumBean)) {
            return 0;
        }
        //2 entity 与bean之间的转换
        PeopleNumEntity peopleNumEntity = new PeopleNumEntity();
        BeanUtils.copyProperties(peopleNumBean ,peopleNumEntity);
        peopleNumBean.setPeopleNumDate(DateUtils.getDate());
        peopleNumBean.setPeopleNumTime(DateUtils.getTime());
        //3 添加在线人数的统计
        try {
            return peopleNumDao.insert(peopleNumEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 通过日期来查询记录
     *  该参数的格式必须是：2018-05-21，
     *                      而且只能查询以前的在线人数，不能查询本天，因为查询本天的在线人数，存在数据的缺失
     * @return List集合
     */
    @Override
    public Map<String, List<Integer>> listPeopleNum() {
        Map<String, List<Integer>> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, - 1);
        Date d = c.getTime();
        String day = format.format(d);

        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date w = c.getTime();
        String week = format.format(w);

        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String month = format.format(m);

        System.out.println("前一个月"+month);
        System.out.println("前一周"+week);
        System.out.println("前一天"+day);

        //查询一天的
        QueryWrapper wrapperDay = new QueryWrapper();
        wrapperDay.eq("peopleNumDate", day);


        //查询一周的
        QueryWrapper wrapperWeek = new QueryWrapper();
        wrapperWeek.gt("peopleNumDate", week);

        //查询一周的
        QueryWrapper wrapperMonth = new QueryWrapper();
        wrapperMonth.gt("peopleNumDate", month);

        List days = null;
        List months = null;
        List weeks = null;
        try {
            days = peopleNumDao.sumPeopleNum(day);
            map.put("day", listToObject(days));

            weeks = peopleNumDao.sumPeopleNum(week);
            map.put("weeks", listToObject(weeks));

            months = peopleNumDao.sumPeopleNum(month);
            map.put("months", listToObject(months));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取集合对象中getPeopleNums的值
     * @return
     */
    private List<Integer> listToObject(List<PeopleNumEntity> peopleNumEntities) {
        List<Integer> ints = new ArrayList<>();
        for (PeopleNumEntity pop : peopleNumEntities) {
            ints.add(pop.getPeopleNums());
        }
        return ints;
    }
}
