package com.cbd.demo.config;

import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.bean.AdministratorBean;
import com.cbd.demo.bean.CompanyBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.entity.PowerEntity;
import com.cbd.demo.service.IAdminService;
import com.cbd.demo.service.IAdministratorService;
import com.cbd.demo.service.ICompanyService;
import com.cbd.demo.service.IUserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {

    //用于用户查询
    @Autowired
    private IAdminService adminService;
    //个人用户
    @Autowired
    private IUserService userService;
    //企业用户
    @Autowired
    private ICompanyService companyService;
    //系统管理员
    @Autowired
    private IAdministratorService administratorService;
    /**
     * 角色权限和对应权限添加U
     * @param principalCollection  身份集合
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name= (String) principalCollection.getPrimaryPrincipal(); //获取主要身份
//        System.out.println("设置权限");
//        System.out.println("传过来的用户名===="+name);
        //查询用户名称
        AdminBean admin = adminService.getAdmin(name);
//        System.out.println("后台的用户名===="+admin.getAdminName());
        //添加权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        System.out.println("======"+admin.getRoleBean());
        if(admin.getRoleBean()!=null){
            //添加角色
//            simpleAuthorizationInfo.addRole(admin.getRoleBean().getRoleName());
            //获取权限
            List<PowerEntity> list= admin.getRoleBean().getPower();
            //循环添加权限
            for (PowerEntity power : list) {
//                System.out.println("权限名字======"+power.getPowerContent());
                //添加权限
                simpleAuthorizationInfo.addStringPermission(power.getPowerContent());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * @param authenticationToken  主体传过来的认证信息
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
//        System.out.println("用户认证");
        //获取用户输入的账号
        String name = (String) authenticationToken.getPrincipal();
        AdminBean admin = adminService.getAdmin(name);
        System.out.println(admin);
        if (admin == null) {
            //这里返回后会报出对应异常
            //           解析出现的错误
            return null;
        } else {
            //将信息放入session中
            HttpServletRequest request=((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
            HttpSession session=request.getSession();
            int in= admin.getAdminRoleType();
            //角色类型 1是个人表，2是企业表，3是管理员表
            switch (in){
                case 1:
                    UserBean user=userService.getUser(admin.getAdminId());
                    session.setAttribute("login",user);
                    break;
                case 2:
                    CompanyBean company= companyService.getCompany(admin.getAdminId());
                    session.setAttribute("login",company);
                    break;
                case 3:
                    AdministratorBean administrator= administratorService.getAdministrator(admin.getAdminId());
                    session.setAttribute("login",administrator);
                    break;
            }

            session.setAttribute("admin",admin);
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    admin.getAdminName(),
                    admin.getAdminPassword(),
                    getName());

            return simpleAuthenticationInfo;
        }
    }
}
