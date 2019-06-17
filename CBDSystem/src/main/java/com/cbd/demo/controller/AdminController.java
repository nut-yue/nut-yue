package com.cbd.demo.controller;

import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.service.IAdminService;
import com.cbd.demo.util.MD5Utils;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : AdminController
 * @Date ：2019/6/3 10:43
 * @Desc ：类的介绍：用户页面控制器
 * @author：作者：王佳伟
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    /**
     * 登录方法
     * @param admin 用户
     * @return
     */
    @Introduce(desc="登录")
    @RequestMapping(value = "getAdmin",method = RequestMethod.GET)
    public ResponseData getAdmin(AdminBean admin,HttpSession session){
        ResponseData responseData = new ResponseData();
//        System.out.println("进来了");
        try {
            //添加认证
            Subject subject = SecurityUtils.getSubject();
            //对传过来的密码进行加密
            String pwd = MD5Utils.md5(admin.getAdminPassword());
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                    admin.getAdminName(),
                    pwd);
            //进行认证
            subject.login(usernamePasswordToken);

            //获取session中的值传给前台
            responseData.getData().put("login",session.getAttribute("login"));
//            responseData.getData().put("admin",session.getAttribute("admin"));
//            System.out.println(responseData);
        }catch (UnknownAccountException UnknownAccount){
            ResponseData responData= new ResponseData(405,"账号不存在");
            return responData;
        }catch (IncorrectCredentialsException IncorrectCredentials){
            ResponseData responData= new ResponseData(404,"密码错误");
            return responData;
        }catch (Exception e){
            ResponseData responData= new ResponseData(406,"炸啦！");
            return responData;
        }
        return responseData;
    }

    /**
     * 修改密码
     * @param id 用户id
     * @param newPwd 新密码
     * @return
     */
    @Introduce(desc="修改密码")
    @RequestMapping(value = "getAdmin",method = RequestMethod.PUT)
    public ResponseData  updateAdmin(int id, String newPwd, HttpSession session){
        AdminBean admin= (AdminBean) session.getAttribute("admin"); //从session中获取id；
//        System.out.println(id+"==="+newPwd);
        int in=adminService.updateAdmin(admin.getAdminId(),newPwd);
        ResponseData responseData = new ResponseData();
        if(in==0){
            responseData.setCode(404);
        }
        return  responseData;
    }
}
