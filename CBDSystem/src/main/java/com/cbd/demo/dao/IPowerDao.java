package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.PowerEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName : IPowerService
 * @Date ：2019/5/28 15:26
 * @Desc ：类的介绍：权限持久化接口
 * @author：作者：王佳伟
 */
public interface IPowerDao {

    /**
     * 根据角色查看全部权限
     * @param reoleId 条件构造器
     * @return 所有权限集合
     */
    List<PowerEntity> listAllPower(int reoleId) throws Exception;
}
