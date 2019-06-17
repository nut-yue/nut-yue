package com.cbd.demo.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.entity.CarportEntity;

/**
 * @ClassName : ICarportService
 * @Date ：2019/5/28 15:37
 * @Desc ：接口的介绍： 个人车位表业务逻辑接口
 * @author：作者： 王佳伟
 */
public interface ICarportService {
    /**
     * 根据个人id，添加个人车位对象，默认车位状态设置为“待审核”
     * @param carport 车位对象
     * @return 返回是否成功，失败返回0 成功返回1
     */
    int saveCarport(CarportBean carport);

    /**
     * 则根据个人用户id查看用户的车位。无论查什么都不能查询车辆状态为“已出售的车辆”
     * 如果state为null，则不就行状态条件查询，如果存在，则需要进行状态条件查询
     * @param id  个人用户id
     * @param currentPage 当前页
     * @param pageSize  显示条数
     * @param state 车位状态
     * @return 车位对象的分页对象
     */
    IPage<CarportEntity> listCarport(int userId, int currentPage, int pageSize, String state);

    /**
     * 根据车位id查询车位信息。
     * @param id 车位id
     * @return 车位对象
     */
    CarportBean getCarport(int carportId);
    /**
     * 根据车位id修改车辆状态为“下架”，同时删除已发布的出售信息或者出租信息，
     * 与该车位相关的预约信息改为“已拒绝”。
     * @param carportBean 车辆对象
     * @return 是否成功，成功返回1,失败返回0
     */
    int updateCarportStatus(CarportBean carportBean);

    /**
     * 根据车位id修改车位状态为“待发布”
     * @param carportBean 车辆对象
     * @return 操作成功条数
     */
    int updateStatus(CarportBean carportBean);

    /**
     * 如果车位上传审核未通过，删除该车位信息
     * @param carportId 车位id
     * @return 操作成功条数
     */
    int deleteCarport(int carportId);
}
