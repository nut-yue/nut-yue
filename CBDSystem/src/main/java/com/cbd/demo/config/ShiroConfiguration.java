package com.cbd.demo.config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤器
 */
@Configuration
public class ShiroConfiguration {
    //将自己的验证方式加入容器
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }
    //权限管理，配置主要是Realm的管理认证
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }
    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> map = new LinkedHashMap<String, String>();
        System.out.println("过滤器");
        //登出 logout是shiro自带的退出当前找好的功能。
         map.put("/logout","logout");
        //登录后台逻辑放行
         map.put("/html/login.html","anon");
         map.put("/admin/getAdmin","anon");

        //个人用户注册放行
        map.put("/user/addUser","anon");
        //js、css、image放行
        map.put("/css/**", "anon");
        map.put("/js/**", "anon");
        map.put("/image/**", "anon");
        //webSocket放行
        map.put("/webSocket/cbd/**","anon");
        map.put("/sms/**","anon");
        //放行三个大模块
        map.put("/html/administrator/**","perms[background]");
        map.put("/html/company/**","perms[management]");
        map.put("/html/user/**","perms[userControl]");
        //后台权限放行
//        map.put("/qiye/**","perms[权限名]");
        //对所有用户认证
        map.put("/**","authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/html/login.html");
        //首页
//        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //错误页面，认证不通过跳转，没有权限就让它重新登录
        shiroFilterFactoryBean.setUnauthorizedUrl("/html/login.html");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map); //将过滤链放入
        return shiroFilterFactoryBean;
    }
    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
