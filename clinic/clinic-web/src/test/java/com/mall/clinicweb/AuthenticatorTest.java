package com.mall.clinicweb;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @ClassName : Authenticator
 * @Date ：2020/2/9 15:12
 * @author：nut-yue
 */
public class AuthenticatorTest {
    // SimpleAccountRealm不支持授权permission,仅能用于进行用户认证和角色校验
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        // 添加用户名jack，密码111111，角色为admin的用户
        simpleAccountRealm.addAccount("jack","111111","admin");
    }

    @Test
    public void authenticatorTest(){
        // 1.创建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 设置SecurityManger指定的Realm
        defaultSecurityManager.setRealm(simpleAccountRealm);
        // 2.主体提交认证请求
        // 设置securityManger环境
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        // 3.创建登录口令，使用主体调用login()进行认证
        UsernamePasswordToken token = new UsernamePasswordToken("jack","111111");
        subject.login(token);
        //数据认证结果
        System.out.println(subject.isAuthenticated());
        // 认证完成后，检测用户是否具有admin这一角色
        subject.checkRole("admin");
        // 用户具有多个角色时，可以使用checkRoles来进行检测
        //subject.checkRoles("admin","user");
        // 登出
        subject.logout();
        System.out.println(subject.isAuthenticated());
    }
}
