package com.mall.clinicweb;

import com.mall.clinicapi.shiro.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;

/**
 * @ClassName : CustomRealmTest
 * @Date ：2020/2/9 22:26
 * @author：nut-yue
 */
public class CustomRealmTest {

    @Test
    public void customRealmTest(){
        CustomRealm customRealm = new CustomRealm();
        // 1.创建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 设置SecurityManger指定的Realm
        defaultSecurityManager.setRealm(customRealm);
        // 2.主体提交认证请求
        // 设置securityManger环境
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        // 3.创建登录口令，使用主体调用login()进行认证
        UsernamePasswordToken token = new UsernamePasswordToken("admin","admin");
        subject.login(token);
        //数据认证结果
        System.out.println(subject.isAuthenticated());
    }
}
