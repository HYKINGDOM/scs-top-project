package com.scs.top.project.framework.shiro;


import com.scs.top.project.module.scs.pojo.SysUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.scs.top.project.framework.shiro.ShiroUtils.isAdminister;

/**
 * 自定义Realm 处理登录 权限
 *
 * @author admin
 */
@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {


    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 判断是否是管理员
        if (isAdminister()) {
            simpleAuthorizationInfo.addRole("admin");
            simpleAuthorizationInfo.addStringPermission("*:*:*");
        } else {

            simpleAuthorizationInfo.addRole("temporary");
            simpleAuthorizationInfo.addStringPermission("*:*:*");
//            // 角色加入AuthorizationInfo认证对象
//            simpleAuthorizationInfo.setRoles(IRoleService.selectRoleKeys());
//            // 权限加入AuthorizationInfo认证对象
//            simpleAuthorizationInfo.setStringPermissions(menuService.selectPermsByUserId());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String userAccount = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }



        SysUserInfo user = new SysUserInfo();
            // 查询用户信息

        return new SimpleAuthenticationInfo(user.getSingleCode(), password, user.getUserName());
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }



}
