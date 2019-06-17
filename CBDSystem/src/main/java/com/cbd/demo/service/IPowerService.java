package com.cbd.demo.service;

import com.cbd.demo.bean.PowerBean;
import com.cbd.demo.entity.PowerEntity;

import java.util.List;

/**
 * @ClassName : IPowerService
 * @Date ：2019/5/28 15:26
 * @Desc ：类的介绍：权限业务接口
 * @author：作者：岳超
 * @chang:吕勇新增查看方法
 */
public interface IPowerService {
    /**
     * 根据角色查看全部权限
     * @param reoleId 条件构造器
     * @return 所有权限集合
     */
    List<PowerEntity> listPower(int reoleId) throws Exception;

}
