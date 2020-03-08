package com.mall.clinicapi.shiro;

import com.mall.clinicdb.entity.Permission;
import com.mall.clinicdb.entity.Role;
import com.mall.clinicdb.entity.User;
import com.mall.clinicservice.user.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;


/**
 * 自定义Realm
 * @ClassName : CustomRealm
 * @Date ：2020/2/9 18:33
 * @author：nut-yue
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    /**
     * 授权
     * @param principalCollection 主体
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.getUserInfo(username);
        if (ObjectUtils.isEmpty(user)){
            return null;
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()){
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            // 添加权限
            for(Permission permission : role.getPermissions()){
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken 认证主体
     * @return 获取到的主体
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 从认证主体中获取用户名
        String username = (String) authenticationToken.getPrincipal();
        // 根据用户名查询用户凭证
        User user= userService.getUserInfo(username);
        if (ObjectUtils.isEmpty(user.getUserName())){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo
                = new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),getName());
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("clinic"));
        return authenticationInfo;
    }
}
