package com.mall.clinicweb;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @ClassName : IniRealmTest
 * @Date ：2020/2/9 17:11
 * @author：nut-yue
 */
public class IniRealmTest {
    // 用户信息（用户名，密码，角色，权限）从resource下的user.ini中读取
    IniRealm iniRealm = new IniRealm("classpath:user.ini");

    @Test
    public void authenticatorTest(){
        // 1.创建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 设置SecurityManger指定的Realm
        defaultSecurityManager.setRealm(iniRealm);
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
        // 认证完成后，校验用户是否拥有user:delete权限
        subject.checkPermission("user:delete");
    }
}
