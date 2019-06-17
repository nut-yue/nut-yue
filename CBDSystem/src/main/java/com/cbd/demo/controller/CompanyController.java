package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.bean.CompanyBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.entity.CompanyEntity;
import com.cbd.demo.service.ICompanyService;
import com.cbd.demo.util.ResponseData;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : CompanyController
 * @Date ：2019/6/3 12:52
 * @Desc ：类的介绍：企业用户页面控制器
 * @author：作者：王佳伟
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    /**
     * 查看企业用户详细信息
     * @param id  用户id
     * @return
     */
    @Introduce(desc="查询企业用户详细信息")
    @RequestMapping(value = "getCompany",method = RequestMethod.GET)
    public ResponseData getCompany(Integer id, HttpSession session){
      CompanyBean company= (CompanyBean) session.getAttribute("login");
      //从session中获取id； 如果sesion中没有，则使用传过来的id
        ResponseData responseData = new ResponseData();
        CompanyBean companyBean= companyService.getCompany(company.getAdminBean().getAdminId());
        if(companyBean!=null){
            responseData.getData().put("company",companyBean);
        }else{
            responseData.setCode(404);
        }
        return  responseData;
    }

    /**
     * 根据企业id查看企业用户详细信息
     * @param id 企业id
     * @return
     */
    @Introduce(desc="查看企业详细信息")
    @RequestMapping(value = "findById",method = RequestMethod.GET)
    public ResponseData findById(Integer id,HttpSession session){
        CompanyBean companyBean = null;
        if(id!=null){
            companyBean= companyService.findById(id);
        }else {
            CompanyBean company= (CompanyBean)  session.getAttribute("login"); //从session中获取id； 如果sesion中没有，则使用传过来的id

            companyBean= companyService.findById(company.getCompanyId());
        }
        ResponseData responseData = new ResponseData();

        if(companyBean!=null){
            responseData.getData().put("company",companyBean);
        }else{
            responseData.setCode(404);
        }
        return  responseData;
    }

    /**
     * 根据用户id删除企业用户
     * @param id 用户id
     * @return
     */
    @Introduce(desc="删除企业用户")
    @RequestMapping(value = "removeCompany",method = RequestMethod.GET)
    public ResponseData removeCompany(Integer id){
        ResponseData responseData = new ResponseData();
        int in= companyService.removeCompany(id);
        if(in==0){
            responseData.setCode(404);
        }
        return  responseData;
    }

    /**
     * 修改企业用户
     * @param administrator
     * @return
     */
    @Introduce(desc="修改企业用户信息")
    @RequestMapping(value = "updateCompany",method = RequestMethod.PUT)
    public ResponseData updateCompany(CompanyBean administrator){
        ResponseData responseData = new ResponseData();
        int in= companyService.updateCompany(administrator);
        if(in==0){
            responseData.setCode(404);
        }
        return  responseData;
    }

    /**
     * 添加用户
     * @param administrator 用户对象
     * @return
     */
    @Introduce(desc="添加企业用户")
    @RequestMapping(value = "saveAdministrator",method = RequestMethod.GET)
    public ResponseData saveAdministrator(CompanyBean administrator,String username,String pwd){
        ResponseData responseData = new ResponseData();
        AdminBean admin = new AdminBean();
        admin.setAdminName(username);
        admin.setAdminPassword(pwd);
        administrator.setAdminBean(admin);
        int in= companyService.saveAdministrator(administrator);
        if(in==0){
            responseData.setCode(404);
        }
        return  responseData;
    }

    /**
     * 分页查询用户
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    @Introduce(desc="查询用户对象")
    @RequestMapping(value = "listCompany",method = RequestMethod.GET)
    public ResponseData listCompany(int currentPage, int pageSize, String name){
        ResponseData responseData = new ResponseData();

        IPage<CompanyEntity> page= companyService.listCompany(currentPage,pageSize,name);
        if(page!=null){
            responseData.getData().put("page",page);
        }else{
            responseData.setCode(404);
        }
        return  responseData;
    }
    @Introduce(desc="查询用户对象")
    @RequestMapping(value = "listCompanys",method = RequestMethod.GET)
    public ResponseData listCompanys(int currentPage, int pageSize,CompanyBean company){
        ResponseData responseData = new ResponseData();
        IPage<CompanyEntity> page= companyService.listCompanys(currentPage,pageSize,company);
        if(page!=null){
            responseData.getData().put("page",page);
        }else{
            responseData.setCode(404);
        }
        return  responseData;
    }
    /**
     * 获取当前登录的用户
     * @return
     */
    @Introduce(desc = "获取当前用户")
    @RequestMapping(value = "getCompany",method = RequestMethod.POST)
    public ResponseData getCompany(HttpSession session){
        CompanyBean company= (CompanyBean) session.getAttribute("login");
        ResponseData responseData = new ResponseData();
        responseData.getData().put("admin",company);
        return responseData;
    }
}
