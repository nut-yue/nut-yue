package com.cbd.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.AdministratorBean;
import com.cbd.demo.entity.AdministratorEntity;
import com.cbd.demo.service.IAdministratorService;
import com.cbd.demo.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @ClassName : AdministratorController
 * @Date ：2019/6/3 12:40
 * @Desc ：类的介绍： 管理员页面控制器
 * @author：作者：王佳伟
 */
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private IAdministratorService administratorService;
    /**
     * 根据用户id查询管理员详细信息
     * @param id
     * @return
     */
   @Introduce(desc="查看管理员的详细信息")
   @RequestMapping(value = "getAdministrator",method = RequestMethod.GET)
   public ResponseData getAdministrator(Integer id, HttpSession session){
       //从session中获取id； 如果sesion中没有，则使用传过来的id
      AdministratorBean administratorSession= (AdministratorBean) session.getAttribute("login");
      //如果穿过来的id为null，则从session中找
      if(id!=null){
          id=administratorSession.getAdministratorId();
      }
      ResponseData responseData = new ResponseData();
      AdministratorBean administrator= administratorService.getAdministrator(id);
      if(administrator!=null){
          responseData.getData().put("administrator",administrator);
      }else{
          responseData.setCode(404);
      }
      return responseData;
   }

    /**
     * 根据管理员id查看管理员信息
     * @param id 管理员id
     * @return
     */
    @Introduce(desc="查看管理员的详细信息")
    @RequestMapping(value = "findById",method = RequestMethod.GET)
    public ResponseData findById(Integer id,HttpSession session){
        //从session中获取id； 如果sesion中没有，则使用传过来的id
        AdministratorBean administratorSession= (AdministratorBean) session.getAttribute("login");
        //如果穿过来的id为null，则从session中找
        if(id!=null){
            id=administratorSession.getAdministratorId();
        }
        ResponseData responseData = new ResponseData();
        AdministratorBean administrator=administratorService.findById(administratorSession.getAdministratorId());
        if(administrator.getAdministratorId()!=0){
            responseData.getData().put("administrator",administrator);
        }else{
            responseData.setCode(404);
        }
        return responseData;
    }

    /**
     * 分页查询管理员用户
     * @param currentPage 当前页
     * @param pageSize 显示条数
     * @return
     */
    @Introduce(desc="查询管理员用户")
    @RequestMapping(value = "listAdministrator",method = RequestMethod.GET)
    public ResponseData listAdministrator(int currentPage, int pageSize){
        ResponseData responseData = new ResponseData();
        IPage<AdministratorEntity> page=administratorService.listAdministrator(currentPage,pageSize);
        if(page!=null){
            responseData.getData().put("page",page);
        }else{
            responseData.setCode(404);
        }
        return responseData;
    }

    /**
     * 修改管理员信息
     * @param administrator 管理员对象
     * @return
     */
    @Introduce(desc="修改管理员信息")
    @RequestMapping(value = "updateAdministrator",method = RequestMethod.PUT)
    public ResponseData updateAdministrator(AdministratorBean administrator){
        ResponseData responseData = new ResponseData();
        int in=administratorService.updateAdministrator(administrator);
        if(in==0){
            responseData.setCode(404);
        }
        return responseData;
    }

    /**
     * 添加管理员对象
     * @param administratorBean 管理员对象
     * @return
     */
    @Introduce(desc="添加管理员")
    @RequestMapping(value = "insertAdministrator",method = RequestMethod.POST)
    public ResponseData insertAdministrator(@RequestBody AdministratorBean administratorBean){
        System.out.println(administratorBean);
        ResponseData responseData = new ResponseData();
        int in=administratorService.insertAdministrator(administratorBean);
        if(in==0){
            responseData.setCode(404);
        }
        return responseData;
    }

    /**
     * 根据管理员id删除管理员对象
     * @param id 管理员id
     * @return
     */
    @Introduce(desc="删除管理员")
    @RequestMapping(value = "delAdministrator",method = RequestMethod.GET)
    public ResponseData delAdministrator(Integer id){
        int in=administratorService.delAdministrator(id);
        if(in==0){
            return ResponseData.unauthorized();
        }
        return  new ResponseData();
    }

    /**
     * 根据管理员id修改管理员权限
     * @param administratorBean 管理员对象
     * @return
     */

    @Introduce(desc="修改管理员的权限")
    @RequestMapping(value = "updateRole",method = RequestMethod.POST)
    public ResponseData updateRole(@RequestBody AdministratorBean administratorBean){

        int in=administratorService.updateRole(administratorBean);
        if(in!=0){
           return new ResponseData();
        }
        return ResponseData.unauthorized();
    }

    /**
     * 获取当前登录的用户
     * @return
     */
    @Introduce(desc = "获取当前用户")
    @RequestMapping(value = "getAdministrator",method = RequestMethod.POST)
    public ResponseData getAdministrator(HttpSession session){
        AdministratorBean administrator= (AdministratorBean) session.getAttribute("login");
        ResponseData responseData = new ResponseData();
        responseData.getData().put("admin",administrator);
        return responseData;
    }
}
