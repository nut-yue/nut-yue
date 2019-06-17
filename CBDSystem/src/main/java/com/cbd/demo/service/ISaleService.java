package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.SaleBean;
import com.cbd.demo.entity.SaleEntity;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @ClassName : ISaleService
 * @Date ：2019/5/28 15:38
 * @Desc ：接口的介绍： 出售信息业务逻辑接口
 * @author：作者：张皓
 */
public interface ISaleService {
    /**
     * 条件查询所有出售车位信息：条件null为查询所有
     * 1、调用出售车位信息dao层方法查询所有出售信息集合
     * 2、遍历集合取出每个车位id，根据车位id调用车位的dao层方法查询车位信息
     * 3、把车位信息封装到出SaleBean里的carportBean里
     * 4、把SaleBean装在List集合里再放到IPage集合里返回。
     * @param carportAddress 车位地址(模糊查询)
     * @param currentPage    起始页数
     * @param pageSize 每页显示数量
     * @return 车位信息分页集合
     */

    IPage<SaleBean> findAllSaleInfo(String carportAddress, int currentPage, int pageSize);

    /**
     * 1、根据出售信息id调用出售车位信息dao层具体的出售信息，取出每个车位id在车位表查车位信息
     * 2、得到entity取出数据封装到bean里
     * @param saleId 出售信息ID
     * @return 信息实体bean
     */
    SaleBean getSale(int saleId);

    /**
     * 先删除该车位的其他出售信息，增加出售信息,同时修改车位表车位状态为“发布出售信息中”
     * @param saleBean 出售信息实体类
     * @return 成功1，失败0
     */
    int addSale(SaleBean saleBean);
    /**
     * 条件查询所有出售车位信息：条件null为查询所有
     * 1、调用出售车位信息dao层方法查询所有出售信息集合
     * 2、遍历集合取出每个车位id，根据车位id调用车位的dao层方法查询车位信息
     * 3、把车位信息封装到出SaleBean里的carportBean里
     * 4、把SaleBean装在List集合里再放到IPage集合里返回。
     * @param userId 当前用户的id
     * @param carportAddress 车位地址(模糊查询)
     * @param currentPage    起始页数
     * @param pageSize 每页显示数量
     * @return 车位信息分页集合
     */
    IPage<SaleBean> findAllSaleInfos(Integer userId, String carportAddress, int currentPage, int pageSize);
}

















