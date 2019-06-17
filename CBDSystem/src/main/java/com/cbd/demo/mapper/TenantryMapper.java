package com.cbd.demo.mapper;/**
 * Created by 123 on 2019/5/28.
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.TenantryEntity;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName : ITenantryService
 * @Date ：2019/5/28 15:29
 * @Desc ：接口的介绍：租户合约的mapper
 * @author：作者： 胡平
 */

public interface TenantryMapper extends SqlMapper, BaseMapper<TenantryEntity> {

    @Select("select sum(tenantryDealPrice) from t_tenantry where companyId=#{id}")
    int getCountMoney(int id);

    @Select("select sum(tenantryDealPrice) from t_tenantry ")
    int getAllCountMoney();
}
