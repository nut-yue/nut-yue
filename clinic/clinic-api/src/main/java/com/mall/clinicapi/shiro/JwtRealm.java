//package com.mall.clinicapi.shiro;
//
//
//import com.mall.clinicapi.jwt.JwtToken;
//import com.mall.clinicapi.jwt.JwtUtil;
//import com.mall.clinicdb.entity.Permission;
//import com.mall.clinicdb.entity.Role;
//import com.mall.clinicdb.entity.User;
//import com.mall.clinicservice.user.IUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.ObjectUtils;
//
//@Slf4j
//public class JwtRealm extends AuthorizingRealm {
//
//    @Autowired
//    private IUserService userService;
//    /*
//     * 多重写一个support
//     * 标识这个Realm是专门用来验证JwtToken
//     * 不负责验证其他的token（UsernamePasswordToken）
//     * */
//    @Override
//    public boolean supports(AuthenticationToken token) {
//        //这个token就是从过滤器中传入的jwtToken
//        return token instanceof JwtToken;
//    }
//
//    //授权
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        String username = JwtUtil.getUsername(principals.toString());
//        User user = userService.getUserInfo(username);
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        for (Role role :user.getRoles()){
//            simpleAuthorizationInfo.addRole(role.getRoleName());
//            for (Permission permission : role.getPermissions()){
//                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
//            }
//        }
//        return simpleAuthorizationInfo;
//    }
//
//    //认证
//    //这个token就是从过滤器中传入的jwtToken
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
//
//        String token = (String) auth.getCredentials();
//        System.out.println(token.toString());
//        String username = JwtUtil.getUsername(token);
//        if (username == null) {
//            throw new AuthenticationException("token invalid");
//        }
//        User user = userService.getUserInfo(username);
//        if (ObjectUtils.isEmpty(user)) {
//            throw new AuthenticationException("user didn't existed");
//        }
//        if (!JwtUtil.verify(token,username,user.getPassword())) {
//            throw new UnknownAccountException("Username or password error");
//        }
//        log.info("在使用token登录"+username);
//        return new SimpleAuthenticationInfo(token,token,"JwtRealm");
//        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名
//    }
//}
