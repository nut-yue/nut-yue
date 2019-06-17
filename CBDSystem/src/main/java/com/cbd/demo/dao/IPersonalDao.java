package com.cbd.demo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.entity.PersonalEntity;

import java.io.Serializable;


/**
 * @ClassName : IPersonalService
 * @Date ：2019/5/31 10:30
 * @Desc ：接口的介绍： 个人买卖合同的dao接口
 * @author：作者：岳超
 */
public interface IPersonalDao {

    /**
     * 添加记录
     * @param personalEntity 个人买卖合同对象
     * @return 操作记录条数
     */
    int insert(PersonalEntity personalEntity) throws Exception;

    /**
     * 条件修改
     * @param personalEntity 修改后的个人合同对象
     * @param updateWrapper 修改条件构造器
     * @return
     */
    int update(PersonalEntity personalEntity, Wrapper<PersonalEntity> updateWrapper) throws  Exception;

    /**
     * 按id查询
     * @param personalId 主键id
     * @return 对象实例
     */
    PersonalEntity selectById(Serializable personalId) throws Exception;

    /**
     * 按条件分页查询个人合同信息
     * @param page 分页条件对象
     * @param queryWrapper 条件构造器
     * @return 分页封装对象
     */
    IPage<PersonalEntity> selectPage(IPage<PersonalEntity> page, Wrapper<PersonalEntity> queryWrapper) throws Exception;


    /**
     * 通过合同id删除合同
     * @param personalId 合同id
     * @return 操作成功条数
     */
    int deleteById(int personalId);
}
