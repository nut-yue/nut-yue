package com.cbd.demo.controller;
import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.AdministratorBean;
import com.cbd.demo.entity.AdministratorEntity;
import com.cbd.demo.entity.RoleEntity;
import com.cbd.demo.service.IRoleService;
import com.cbd.demo.util.ResponseData;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName : RoleController
 * @Date ：2019/6/3 12:48
 * @Desc ：类的介绍：
 * @author：作者：陈玉婷
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @Introduce(desc="创建新的角色")
    @RequestMapping(value = "addRole",method = RequestMethod.GET)
    public ResponseData addRole(RoleEntity roleEntity){
        ResponseData responseData = new ResponseData();
        int myRole=roleService.addRole(roleEntity);
        if (myRole !=0){

            responseData.getData().put("addRole",myRole);

            return responseData;
        }

        return ResponseData.unauthorized();
    }

    @Introduce(desc="创建用户时，查询所有的角色对象")
    @RequestMapping(value = "showAllRole",method = RequestMethod.GET)
    public ResponseData showAllRole(){
        ResponseData responseData = new ResponseData();
        List<RoleEntity> roleEntities=roleService.showAllRole();
        if (roleEntities != null){
            responseData.getData().put("showAllRole",roleEntities);
            return responseData;
        }
        return ResponseData.unauthorized();
    }

    /**
     * 查看角色名是否重复
     * @param name 角色名字
     * @return 如果count是-1则是报异常了，如果是0,则是没有重复。
     */
    @Introduce(desc=" 查看角色名是否重复")
    @RequestMapping(value = "getRoleCount",method = RequestMethod.GET)
    public ResponseData getRoleCount(String name){
        int in= roleService.getRoleCount(name);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("count",in);
        return responseData;
    }

    /**
     * 根据角色信息，修改角色权限
     * @param role
     * @return
     */
    @Introduce(desc="修改角色权限")
    @RequestMapping(value = "updatePower",method = RequestMethod.GET)
    public ResponseData updatePower(RoleEntity role){
        int in =roleService.updateRole(role);
        ResponseData responseData = new ResponseData();
        responseData.getData().put("count",in);
        return responseData;
    }

    /**
     * 根据用户id查看管理员角色和权限
     * @param id 管理员id
     * @return
     */
    @Introduce(desc="查询用户权限")
    @RequestMapping(value = "getPower",method = RequestMethod.GET)
    public ResponseData getRole(Integer id, HttpSession session){
        ResponseData responseData = new ResponseData();
        AdministratorBean administrator= (AdministratorBean) session.getAttribute("login");
        if(id==null){
            id=administrator.getAdminBean().getAdminId();
        }
        RoleEntity role= roleService.getRole(id);
        responseData.getData().put("role",role.getPower());
        return responseData;
    }
}
