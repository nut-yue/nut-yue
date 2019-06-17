package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.CarportBean;
import com.cbd.demo.bean.RentBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.service.IRentService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : RentConroller
 * @Date ：2019/6/2 13:46
 * @Desc ：类的介绍：出租展示信息页面控制器
 * @author：岳超
 */
@RestController
@RequestMapping("/rent")
public class RentController {

    @Autowired
    private IRentService rentService;

    /**
     * 测试通过(参数说明：ajax需要传入开始时间，结束时间，价格，及车位id)
     * 发布招租信息
     * @param rentBean 招租信息对象
     * @param carportId 车位id
     * @return 响应对象
     */
    @Introduce(desc = "发布招租信息")
    @RequestMapping(value = "saveRent",method = RequestMethod.POST)
    public ResponseData saveRent(RentBean rentBean,int carportId){
        // 创建车位对象Bean
        CarportBean carportBean = new CarportBean();
        // 设置招租信息中车位对象的车位id
        carportBean.setCarportId(carportId);
        rentBean.setCarportBean(carportBean);
        // 新增出租信息
        int result = rentService.insertRent(rentBean);
        if(result==1){
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过(参数说明：ajax需要传入租借信息id)
     * 查看招租详细信息
     * @param rentId 招租信息id
     * @return 响应对象
     */
    @Introduce(desc = "查看出租信息详情")
    @RequestMapping(value = "getOne",method = RequestMethod.GET)
    public ResponseData getRent(Integer rentId){
        // 得到出租信息对象
        RentBean rentBean = rentService.getRent(rentId);
        if(rentBean!=null) {
            // 创建响应对象
            ResponseData responseData = new ResponseData();
            // 将出租信息封装到响应对象中
            responseData.getData().put("rentBean", rentBean);
            // 返回响应对象
            return responseData;
        }
        return ResponseData.notFound();
    }

    /**
     * 测试通过(参数说明：ajax必传参数，当前页和每页显示条数，选填参数车位地址)
     * 根据车位地址分页查询招租信息
     * @param carportAddress 车位地址
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @return 响应对象
     */

    @Introduce(desc = "查看所有出租信息")
    @RequestMapping(value = "listRent",method = RequestMethod.GET)
    public ResponseData listRent(String carportAddress, Integer currentPage, Integer pageSize, HttpSession session){
        UserBean user= (UserBean) session.getAttribute("login");
        // 获得分页对象
        IPage<RentBean> rentPage = rentService.listRent(user.getUserId(),carportAddress, currentPage, pageSize);
        System.out.println(rentPage);
        if(rentPage!=null) {
            // 创建响应对象
            ResponseData responseData = new ResponseData();
            // 将分页对象封装到响应对象中
            responseData.getData().put("page", rentPage);
            // 返回响应对象
            return responseData;
        }
        return ResponseData.notFound();
    }
}
