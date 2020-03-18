package com.mall.clinicmall_db.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.clinicmall_db.entity.AdminEntity;

import java.util.Map;

/**
 * @ClassName : AdminService
 * @Date ：2020/3/14 22:04
 * @author：nut-yue
 */
public interface AdminService {

    /**
     * 根据id查询admin信息
     */
    AdminEntity getAdmin (String id);

    /**
     * 根据map中的新，按条件查询admin信息
     */
    AdminEntity getAdmin (Map<String,Object> condition);

    /**
     * 查询admin列表信息
     */
    IPage<AdminEntity> listAdmin(Map<String,Object> condition);

    /**
     * 删除admin
     */
    int deleteAdmin (String id);

    /**
     * 新增admin信息
     */
    int insertAdmin (AdminEntity adminEntity);

    /**
     * 该方法为根据id修改admin其余所有的信息
     */
    int updateAdmin (AdminEntity adminEntity);

    int updateAdmin (Map<String,Object> condition);

}
