package com.mall.clinicapi.controller.user;

import cn.hutool.core.map.MapUtil;
import com.mall.clinicutil.data.ResponseData;
import com.mall.clinicdb.entity.UserEntity;
import com.mall.clinicservice.user.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName : UserController
 * @Date ：2019/12/27 15:35
 * @author：nut-yue
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    private static Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ApiOperation(value = "登陆",notes = "后台管理系统登陆方法",response = ResponseData.class)
    public ResponseData login(@RequestParam Map<String,Object> condition){
        String username = MapUtil.getStr(condition,"username");
        String password = MapUtil.getStr(condition,"password");
        // 添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        ResponseData responseData = new ResponseData();
        try {
            // 进行验证，可以捕获异常，返回对应的信息
            subject.login(usernamePasswordToken);
            // 这里可以验证角色和权限
//            subject.checkRole("admin");
//            subject.checkPermission("admin");
        } catch (UnknownAccountException UnknownAccount) {
            ResponseData responsData= new ResponseData(404,"账号不存在");
            return responsData;
        } catch (IncorrectCredentialsException IncorrectCredentials) {
            ResponseData responsData= new ResponseData(404,"密码错误");
            return responsData;
        } finally {
            logger.info(subject.getPrincipal()+"进行登陆");
        }
        return ResponseData.ok();
    }


    @RequestMapping(value = "/insertUser",method = RequestMethod.PUT)
    @ApiOperation(value = "注册新的用户",notes = "创建新的用户账号信息",response = ResponseData.class)
    public ResponseData insertUser(UserEntity userEntity){
        int result = userService.insertUser(userEntity);
        if (result!=0) {
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.DELETE)
    @ApiOperation(value="删除用户",response = ResponseData.class)
    @ApiImplicitParam(name = "userId", value = "用户id")
    public ResponseData deleteUser(int userId){
        int result = userService.deleteUser(userId);
        if (result!=0) {
            return ResponseData.ok();
        }
        return ResponseData.notFound();
    }

    /**
     * 返回当前认证用户信息
     * @return 返回用户信息
     */
    @RequestMapping("/getSubject")
    public ResponseData getSubject(){
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
        ResponseData responseData = new ResponseData(200,"");
        responseData.getData().put("user",principals);
        return responseData;
    }

}
