package com.cbd.demo.dao;/**
 * Created by 123 on 2019/5/28.
 */

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.cbd.demo.entity.PeopleNumEntity;

import java.util.List;

/**
 * @ClassName : IPeopleNumService
 * @Date ：2019/5/28 15:31
 * @Desc ：接口的介绍：在线人数统计的dao接口
 * @author：作者： 胡平
 */
public interface IPeopleNumDao {

    /**
     * 添加记录
     * @param peopleNumEntity 对象实例
     * @return 操作记录条数
     */
    int insert(PeopleNumEntity peopleNumEntity) throws Exception;

    /**
     * 条件查询在线统计人数列表
     * @param queryWrapper 条件构造器
     * @return 用户集合
     */
    List<PeopleNumEntity> selectList(Wrapper<PeopleNumEntity> queryWrapper)throws Exception;

    /**
     * 统计某一个时间段的所以人数 例如09:00:00 这个时间段的 一周内的所有人数
     * @param peopleNumTime 时间段
     * @return
     */
    List<PeopleNumEntity> sumPeopleNum(String peopleNumTime);
}
