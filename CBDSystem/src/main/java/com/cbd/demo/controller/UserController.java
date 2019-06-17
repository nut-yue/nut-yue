package com.cbd.demo.controller;

import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.AdministratorBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.service.IUserService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Repeatable;

/**
 * @ClassName : UserController
 * @Date ：2019/5/31 19:43
 * @Desc ：类的介绍：表现层接口
 * @author：作者：周陆成
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;

    /**
     * 将数据进行封装 查看用户个人信息及信誉度
     * @return 用户对象
     */
    @Introduce(desc = "获取当前对象信息")
    @RequestMapping(value = "reputation",method = RequestMethod.GET)
    public ResponseData findReputation(HttpSession session){
        //从Session里取得ID
        UserBean login=(UserBean)session.getAttribute("login");
        //int id=userBean.getUserId;
        //这里定死的ID

        ResponseData responseData = new ResponseData();
        UserBean userBean=service.getUserId(login.getUserId());
        if (userBean != null) {
            responseData.getData().put("user",userBean);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 修改用户信息
     * @param userBean 用户对象信息
     * @return 是否修改成功
     */

    @Introduce(desc = "获取当前修改用户信息")
    @RequestMapping(value = "updateUser",method = RequestMethod.GET)
    public ResponseData updUser(UserBean userBean){
        ResponseData responseData = new ResponseData();
        int sel=service.updateUser(userBean);
        if(sel!=0){
            responseData.getData().put("sel",sel);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 添加用户对象
     * @param userBean 用户对象
     * @return 对或者错 添加成功或添加失败
     */
    @Introduce(desc = "添加用户信息")
    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    public ResponseData addUser(@RequestBody UserBean userBean){
        System.out.println(userBean);
        ResponseData responseData=new ResponseData();
        int add=service.saveUser(userBean);
            if(add>0){
                responseData.getData().put("addUser",true);
                return responseData;
            }
        responseData.getData().put("addUser",false);
        return responseData;
    }
    /**
     * 获取当前登录的用户
     * @return
     */
    @Introduce(desc = "获取当前用户")
    @RequestMapping(value = "getUser",method = RequestMethod.POST)
    public ResponseData getUser(HttpSession session){
        UserBean user= (UserBean) session.getAttribute("login");
        ResponseData responseData = new ResponseData();
        responseData.getData().put("admin",user);
        return responseData;
    }
}
