package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.entity.CarportEntity;
import com.cbd.demo.service.ICarportService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : CarportController
 * @Date ：2019/6/2 14:04
 * @Desc ：类的介绍：个人车位页面控制器
 * @author：岳超
 */
@RestController
@RequestMapping("/carport")
public class CarportController {

    @Autowired
    private ICarportService carportService;

    /**
     * 测试通过(参数说明：ajax参数列表中必须有车位id)
     * 添加新的车位，状态为待审核
     * @param carportBean 车位对象
     * @return 响应对象
     */
    @Introduce(desc="添加新车位")
    @RequestMapping(value = "saveCarport",method = RequestMethod.GET)
    public ResponseData saveCarport(CarportBean carportBean, HttpSession session){
         UserBean userBean = (UserBean) session.getAttribute("login");
        // 测试手动设置
        carportBean.setUserBean(userBean);
        // 得到操作成功条数
        int result = carportService.saveCarport(carportBean);
        // 根据操作成功条数决定响应对象结果
        if(result==1){
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过(参数说明：ajax至少包括所属人id,当前页，每页显示条件,车位状态为选填参数)
     * 如果查询全部,userId传0,查询当前用户传1
     * 根据车位状态查询车位
     * @param userId 所属人id
     * @param currentPage 当前页
     * @param pageSize 没有显示条数
     * @param state 车位状态
     * @return 响应对象
     */
    @Introduce(desc="查询个人车位列表")
    @RequestMapping(value = "listCarport",method = RequestMethod.GET)
    public ResponseData listCarport(int userId, int currentPage, int pageSize, String state,HttpSession session){

        // 获得车位分页对象
        IPage<CarportEntity> carportPage = new Page<>();
        if(userId!=0){
            // 获得session中的用户对象
            UserBean user= (UserBean) session.getAttribute("login");
            // 给id赋值
            int id = 0;
            id = user.getUserId();
            carportPage=carportService.listCarport(id, currentPage, pageSize, state);
        }else {
            carportPage=carportService.listCarport(userId, currentPage, pageSize, state);
        }

        if(carportPage!=null){
            // 创建响应对象
            ResponseData responseData = new ResponseData();
            // 将分页对象封装到响应对象中
            responseData.getData().put("carports",carportPage);
            return responseData;
        }
        return ResponseData.notFound();
    }

    /**
     * 已测试通过(参数说明：ajax必须包含车位id)
     * 根据车位id查看车位详细信息
     * @param carportId 车位id
     * @return 响应对象
     */
    @Introduce(desc = "查看车位详情")
    @RequestMapping(value = "getCarport",method = RequestMethod.GET)
    public ResponseData getCarport(int carportId){
        // 通过车位id查询车位详细信息
        CarportBean carport = carportService.getCarport(carportId);
        if(carport!=null){
            // 创建响应对象
            ResponseData responseData = new ResponseData();
            // 将车位对象放入相应对象
            responseData.getData().put("carport",carport);
            return responseData;
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过(参数说明：ajax中至少传车位id和车位当前状态)
     * 下架车位
     * @param carportBean 车位对象
     * @return
     */
    @Introduce(desc="下架车位")
    @RequestMapping(value="soldOut",method = RequestMethod.PUT)
    public ResponseData updateCarportStatus(CarportBean carportBean){
        // 获得操作成功条数
        int result = carportService.updateCarportStatus(carportBean);
        if(result==1){
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过(参数说明：ajax参数必须包括车位id和车位当前状态)
     * 个人车位审核车位
     * @param carportBean 车位对象
     * @return 响应对象
     */
    @Introduce(desc = "审核个人车位")
    @RequestMapping(value = "check",method = RequestMethod.PUT)
    public ResponseData check(CarportBean carportBean){
        // 获得车位审核通过操作条数
        int result = carportService.updateStatus(carportBean);
        if(result==1){
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过(参数说明：ajax传值必须包括车位id)
     * 拒绝申请，删除车位信息
     * @param carportId 车位id
     * @return 响应对象
     */
    @Introduce(desc="拒绝申请")
    @RequestMapping(value = "deleteCarport",method = RequestMethod.DELETE)
    public ResponseData deleteCarport(int carportId){
        int result = carportService.deleteCarport(carportId);
        if(result==1){
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }
}

