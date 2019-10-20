package com.scs.top.project.framework.shiro;

import com.isoftstone.pmit.common.util.DateUtils;
import com.isoftstone.pmit.common.util.StringUtils;
import com.isoftstone.pmit.system.menu.service.MenuService;
import com.isoftstone.pmit.system.role.service.IRoleService;
import com.isoftstone.pmit.system.user.entity.User;
import com.isoftstone.pmit.system.user.service.IUserService;
import com.scs.top.project.common.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.isoftstone.pmit.framework.shiro.ShiroUtils.isAdminister;
import static com.scs.top.project.framework.shiro.ShiroUtils.isAdminister;

/**
 * 自定义Realm 处理登录 权限
 *
 * @author admin
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

//    @Autowired
//    private MenuService menuService;
//
//    @Autowired
//    private IRoleService IRoleService;
//
//    @Autowired
//    private IUserService IUserService;

    /**
     * ipsa登录的接口
     */
    public static final String IPSA_URL = "https://passport.isoftstone.com/?DomainUrl=http://ipsapro.isoftstone.com&Ret&ReturnUrl=%2fPortal#";
    /**
     * 登录成功后返回的地址
     */
    public static final String IPSA_RETURN_URL = "http://ipsapro.isoftstone.com/Portal";

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



        User user = null;
            // 查询用户信息
            user = IUserService.selectUserByLoginName(userAccount);
            if (!StringUtils.isNotNull(user)) {
                //用户不存在,将从pmoit.ipsaInfo 表中获取数据
                user = IUserService.selectPmoitUserByLoginName(userAccount);
                //throw new UnknownAccountException("用户账户没有权限");
            }

        return new SimpleAuthenticationInfo(user.getUserAccount(), password, user.getUserName());
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }


    /**
     * 记录登录信息
     */
    private void recordLoginInfo(User user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        user.setUpdateBy(user.getUserAccount());
        IUserService.updateUser(user);
    }

}
