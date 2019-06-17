package com.cbd.demo.controller;

import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.entity.PersonalEntity;
import com.cbd.demo.service.IPersonalService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : PersonalController
 * @Date ：2019/6/3 10:00
 * @Desc ：个人买卖合同的页面监听器
 * @author：作者：刘划轩
 */
@RestController
@RequestMapping("/personal")
public class PersonalController {
    @Autowired
    private IPersonalService personalService;


    @Introduce(desc="新增个人买卖合同")
    @RequestMapping(value = "addPersonal",method = RequestMethod.GET)
    public ResponseData addPersonal(PersonalEntity personal){
        ResponseData responseData = new ResponseData();
        int i=personalService.addPersonal(personal);
        responseData.getData().put("isSuccess",i);
        return responseData;
    }

    @Introduce(desc="通过合同id查看合同详情")
    @RequestMapping(value = "getPersonal",method = RequestMethod.GET)
    public ResponseData getPersonal(int personalId,HttpSession session){
        UserBean user= (UserBean) session.getAttribute("login");
        ResponseData responseData = new ResponseData();
        responseData.getData().put("bean",personalService.getPersonal(personalId));
        responseData.getData().put("userId",user.getUserId());
        return responseData;
    }

    @Introduce(desc="通过合同id查看合同详情")
    @RequestMapping(value = "getCBDPersonal",method = RequestMethod.GET)
    public ResponseData getCBDPersonal(int personalId){
        ResponseData responseData = new ResponseData();
        responseData.getData().put("bean",personalService.getPersonal(personalId));
        return responseData;
    }

    @Introduce(desc="用户签约个人买卖合同")
    @RequestMapping(value = "updateSelfPersonalSigning",method = RequestMethod.GET)
    public ResponseData updateSelfPersonalSigning(PersonalEntity personal){
        System.out.println(personal);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("isSuccess",personalService.updateSelfPersonalSigning(personal));
        return responseData;
    }

    @Introduce(desc="用户拒绝签署合同")
    @RequestMapping(value = "updatePersonalSigning",method = RequestMethod.GET)
    public ResponseData updatePersonalSigning(PersonalEntity personal){
        ResponseData responseData = new ResponseData();
        responseData.getData().put("isSuccess",personalService.updatePersonalSigning(personal));
        return responseData;
    }

    @Introduce(desc="通过用户ID查看该用户买卖合同")
    @RequestMapping(value = "listPersonal",method = RequestMethod.GET)
    public ResponseData listPersonal(HttpSession session, int currentPage, int pageSize){
        ResponseData responseData = new ResponseData();
        UserBean userBean=(UserBean)session.getAttribute("login");
        responseData.getData().put("page",personalService.listPersonal(userBean.getUserId(),currentPage,pageSize));
        return responseData;
    }

    @Introduce(desc="动态条件查询个人合同")
    @RequestMapping(value = "listPersonalByCondition",method = RequestMethod.GET)
    public ResponseData listPersonalByCondition(HttpSession session,int buyUserId, int sellUserId, int currentPage, int pageSize){
        ResponseData responseData = new ResponseData();
        UserBean userBean=(UserBean)session.getAttribute("login");
        if (buyUserId!=0){
            //查看个人用户作为买方的合同
            responseData.getData().put("page",personalService.listPersonalByCondition(userBean.getUserId(),0,currentPage,pageSize));
        }else if (sellUserId!=0){
            //查看个人用户作为卖方的合同
            responseData.getData().put("page",personalService.listPersonalByCondition(0,userBean.getUserId(),currentPage,pageSize));
        }else {
            //查看所有个人买卖合同
            responseData.getData().put("page",personalService.listPersonalByCondition(userBean.getUserId(),userBean.getUserId(),currentPage,pageSize));
        }
        return responseData;
    }

    @Introduce(desc="企业查看所有个人合同")
    @RequestMapping(value = "listAllPersonal",method = RequestMethod.GET)
    public ResponseData listAllPersonal(int currentPage, int pageSize){
        ResponseData responseData = new ResponseData();
        responseData.getData().put("page",personalService.listAllPersonal(currentPage,pageSize));
        return responseData;
    }
}
