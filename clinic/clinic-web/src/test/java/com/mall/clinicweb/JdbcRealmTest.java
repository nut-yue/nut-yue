package com.mall.clinicweb;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @ClassName : JdbcRealmTest
 * @Date ：2020/2/9 17:28
 * @author：nut-yue
 */
public class JdbcRealmTest {
    DruidDataSource druidDataSource = new DruidDataSource();
    {
        druidDataSource.setUrl("jdbc:mysql://182.61.149.29:3306/clinic?characterEncoding=utf8&serverTimezone=UTC");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("Tt@123456");
    }
    @Test
    public void authenticatorTest(){
        // 创建一个jdbcRealm，认证信息从数据库中读取
        JdbcRealm jdbcRealm = new JdbcRealm();
        // 设置数据源
        jdbcRealm.setDataSource(druidDataSource);
        /*
        * 此处不书写sql是因为jdbcRealm默认书写了查询用户，角色及权限的sql，若表名和字段名按照
        * 默认的命名则可以不用书写sql就可以直接进行查询，但是往往实际应用中，表名和字段名往往和
        * 默认的不一致，此时需要可以自己写sql，操作如下
        * */
        // 此为自定义sql进行用户认证，另外权限及角色查询类似
        String userSql = "select password from clinic_user where username = ?";
        jdbcRealm.setAuthenticationQuery(userSql);
        // 1.创建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 设置SecurityManger指定的Realm
        defaultSecurityManager.setRealm(jdbcRealm);
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
