package com.cbd.demo.mapper;/**
 * Created by 123 on 2019/5/28.
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.ExternalEntity;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName : IExternalService
 * @Date ：2019/5/28 15:27
 * @Desc ：接口的介绍：外部合约的mapper
 * @author：作者： 胡平
 */
public interface ExternalMapper extends SqlMapper,BaseMapper<ExternalEntity>{

    @Select("select sum(externalPrice) from t_external where externalId=#{id}")
    int getCountMoney(int id);

    @Select("select sum(externalPrice) from t_external ")
    int getAllCountMoney();

    @Select("select LAST_INSERT_ID()")
    int getInsertId();
}
