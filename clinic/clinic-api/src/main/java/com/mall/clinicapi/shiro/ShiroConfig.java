package com.mall.clinicapi.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName : ShiroConfig
 * @Date ：2020/2/10 20:43
 * @author：nut-yue
 */
@Configuration
public class ShiroConfig {

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher (){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }



    // 将自定义realm加入容器
    @Bean
    public CustomRealm customRealm(HashedCredentialsMatcher credentialsMatcher){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setAuthenticationCachingEnabled(false);
        customRealm.setCredentialsMatcher(credentialsMatcher);
        return customRealm;
    }

    // 权限管理，创建SecurityManger环境，并指定自定义Realm
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomRealm customRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm);
        return defaultWebSecurityManager;
    }

    // Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 当前用户为非认证用户时，需要先进行登陆认证
        shiroFilterFactoryBean.setLoginUrl("/login/login.html");
        //错误页面，认证不通过跳转，没有权限就让它重新登录
        shiroFilterFactoryBean.setUnauthorizedUrl("/login/login.html");
        Map<String, String> map = new LinkedHashMap<>();
        // 下边表示运行匿名访问的资源，前面时资源的路径，后面是是否运行匿名访问，anon表示允许，authc表示必须授权才能访问
        map.put("/login/login.js","anon");
        map.put("/user/login","anon");
        map.put("/logout","logout");//logout"表示退出
        map.put("/**","authc");//必须授权才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
